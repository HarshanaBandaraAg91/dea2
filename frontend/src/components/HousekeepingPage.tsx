import React, { useEffect, useState } from 'react';
import './HousekeepingPage.css';

const API_BASE = 'http://localhost:8086/tasks';

interface HousekeepingTask {
  id?: number;
  roomNumber: string;
  taskType: string;
  status: string;
  assignedStaff: string;
  taskDate: string;
  notes: string;
}

const emptyTask: HousekeepingTask = {
  roomNumber: '',
  taskType: '',
  status: 'PENDING',
  assignedStaff: '',
  taskDate: new Date().toISOString().split('T')[0],
  notes: '',
};

const TASK_TYPES = ['CLEANING', 'MAINTENANCE', 'LAUNDRY', 'INSPECTION', 'TURNOVER', 'DEEP_CLEAN'];
const STATUSES = ['PENDING', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED'];

export default function HousekeepingPage() {
  const [tasks, setTasks] = useState<HousekeepingTask[]>([]);
  const [form, setForm] = useState<HousekeepingTask>(emptyTask);
  const [editingId, setEditingId] = useState<number | null>(null);
  const [showForm, setShowForm] = useState(false);
  const [filterRoom, setFilterRoom] = useState('');
  const [filterStatus, setFilterStatus] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [successMsg, setSuccessMsg] = useState('');

  useEffect(() => {
    fetchTasks();
  }, []);

  async function fetchTasks() {
    setLoading(true);
    setError('');
    try {
      const res = await fetch(API_BASE);
      if (!res.ok) throw new Error('Failed to fetch tasks');
      const data = await res.json();
      setTasks(data);
    } catch (e: any) {
      setError('Could not connect to housekeeping service. Make sure the backend is running on port 8086.');
    } finally {
      setLoading(false);
    }
  }

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    setError('');
    try {
      const url = editingId ? `${API_BASE}/${editingId}` : API_BASE;
      const method = editingId ? 'PUT' : 'POST';
      const res = await fetch(url, {
        method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(form),
      });
      if (!res.ok) throw new Error('Failed to save task');
      showSuccess(editingId ? 'Task updated successfully!' : 'Task created successfully!');
      resetForm();
      fetchTasks();
    } catch (e: any) {
      setError(e.message);
    }
  }

  async function handleDelete(id: number) {
    if (!window.confirm('Delete this task?')) return;
    setError('');
    try {
      const res = await fetch(`${API_BASE}/${id}`, { method: 'DELETE' });
      if (!res.ok) throw new Error('Failed to delete task');
      showSuccess('Task deleted.');
      fetchTasks();
    } catch (e: any) {
      setError(e.message);
    }
  }

  function handleEdit(task: HousekeepingTask) {
    setForm({ ...task });
    setEditingId(task.id!);
    setShowForm(true);
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  function resetForm() {
    setForm(emptyTask);
    setEditingId(null);
    setShowForm(false);
  }

  function showSuccess(msg: string) {
    setSuccessMsg(msg);
    setTimeout(() => setSuccessMsg(''), 3000);
  }

  const filtered = tasks.filter((t) => {
    const roomMatch = filterRoom === '' || t.roomNumber.toLowerCase().includes(filterRoom.toLowerCase());
    const statusMatch = filterStatus === '' || t.status === filterStatus;
    return roomMatch && statusMatch;
  });

  return (
    <div className="hk-page">
      {/* Header */}
      <div className="hk-header">
        <div className="hk-header-left">
          <span className="hk-icon">🧹</span>
          <div>
            <h1>Housekeeping Management</h1>
            <p>Manage room cleaning and maintenance tasks</p>
          </div>
        </div>
        <button className="hk-btn hk-btn-primary" onClick={() => { resetForm(); setShowForm(true); }}>
          + New Task
        </button>
      </div>

      {/* Alerts */}
      {error && <div className="hk-alert hk-alert-error">⚠️ {error}</div>}
      {successMsg && <div className="hk-alert hk-alert-success">✅ {successMsg}</div>}

      {/* Task Form */}
      {showForm && (
        <div className="hk-card hk-form-card">
          <div className="hk-card-header">
            <h2>{editingId ? 'Edit Task' : 'Create New Task'}</h2>
            <button className="hk-btn-close" onClick={resetForm}>✕</button>
          </div>
          <form onSubmit={handleSubmit} className="hk-form">
            <div className="hk-form-grid">
              <div className="hk-form-group">
                <label>Room Number *</label>
                <input
                  required
                  placeholder="e.g. 101"
                  value={form.roomNumber}
                  onChange={(e) => setForm({ ...form, roomNumber: e.target.value })}
                />
              </div>
              <div className="hk-form-group">
                <label>Task Type *</label>
                <select
                  required
                  value={form.taskType}
                  onChange={(e) => setForm({ ...form, taskType: e.target.value })}
                >
                  <option value="">Select task type</option>
                  {TASK_TYPES.map((t) => <option key={t} value={t}>{t.replace('_', ' ')}</option>)}
                </select>
              </div>
              <div className="hk-form-group">
                <label>Status *</label>
                <select
                  required
                  value={form.status}
                  onChange={(e) => setForm({ ...form, status: e.target.value })}
                >
                  {STATUSES.map((s) => <option key={s} value={s}>{s.replace('_', ' ')}</option>)}
                </select>
              </div>
              <div className="hk-form-group">
                <label>Assigned Staff *</label>
                <input
                  required
                  placeholder="Staff name"
                  value={form.assignedStaff}
                  onChange={(e) => setForm({ ...form, assignedStaff: e.target.value })}
                />
              </div>
              <div className="hk-form-group">
                <label>Task Date *</label>
                <input
                  type="date"
                  required
                  value={form.taskDate}
                  onChange={(e) => setForm({ ...form, taskDate: e.target.value })}
                />
              </div>
              <div className="hk-form-group hk-form-group-full">
                <label>Notes</label>
                <textarea
                  rows={3}
                  placeholder="Additional notes..."
                  value={form.notes}
                  onChange={(e) => setForm({ ...form, notes: e.target.value })}
                />
              </div>
            </div>
            <div className="hk-form-actions">
              <button type="button" className="hk-btn hk-btn-secondary" onClick={resetForm}>
                Cancel
              </button>
              <button type="submit" className="hk-btn hk-btn-primary">
                {editingId ? 'Update Task' : 'Create Task'}
              </button>
            </div>
          </form>
        </div>
      )}

      {/* Stats */}
      <div className="hk-stats">
        {STATUSES.map((s) => (
          <div key={s} className={`hk-stat-card hk-stat-${s.toLowerCase()}`}>
            <span className="hk-stat-count">{tasks.filter((t) => t.status === s).length}</span>
            <span className="hk-stat-label">{s.replace('_', ' ')}</span>
          </div>
        ))}
        <div className="hk-stat-card hk-stat-total">
          <span className="hk-stat-count">{tasks.length}</span>
          <span className="hk-stat-label">TOTAL</span>
        </div>
      </div>

      {/* Filters */}
      <div className="hk-filters">
        <input
          className="hk-filter-input"
          placeholder="🔍 Filter by room number..."
          value={filterRoom}
          onChange={(e) => setFilterRoom(e.target.value)}
        />
        <select
          className="hk-filter-select"
          value={filterStatus}
          onChange={(e) => setFilterStatus(e.target.value)}
        >
          <option value="">All Statuses</option>
          {STATUSES.map((s) => <option key={s} value={s}>{s.replace('_', ' ')}</option>)}
        </select>
        <button className="hk-btn hk-btn-secondary" onClick={fetchTasks}>
          ↻ Refresh
        </button>
      </div>

      {/* Tasks Table */}
      <div className="hk-card">
        {loading ? (
          <div className="hk-loading">Loading tasks...</div>
        ) : filtered.length === 0 ? (
          <div className="hk-empty">
            <span>🏨</span>
            <p>{tasks.length === 0 ? 'No tasks yet. Create your first task!' : 'No tasks match the filter.'}</p>
          </div>
        ) : (
          <div className="hk-table-wrapper">
            <table className="hk-table">
              <thead>
                <tr>
                  <th>#</th>
                  <th>Room</th>
                  <th>Task Type</th>
                  <th>Status</th>
                  <th>Assigned Staff</th>
                  <th>Date</th>
                  <th>Notes</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {filtered.map((task) => (
                  <tr key={task.id}>
                    <td className="hk-td-id">{task.id}</td>
                    <td><strong>{task.roomNumber}</strong></td>
                    <td>{task.taskType.replace('_', ' ')}</td>
                    <td>
                      <span className={`hk-badge hk-badge-${task.status.toLowerCase()}`}>
                        {task.status.replace('_', ' ')}
                      </span>
                    </td>
                    <td>{task.assignedStaff}</td>
                    <td>{task.taskDate}</td>
                    <td className="hk-td-notes">{task.notes || '—'}</td>
                    <td>
                      <div className="hk-actions">
                        <button
                          className="hk-btn hk-btn-edit"
                          onClick={() => handleEdit(task)}
                          title="Edit"
                        >
                          ✏️
                        </button>
                        <button
                          className="hk-btn hk-btn-delete"
                          onClick={() => handleDelete(task.id!)}
                          title="Delete"
                        >
                          🗑️
                        </button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  );
}
