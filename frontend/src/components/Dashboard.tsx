import React from 'react';
import './Dashboard.css';

type Section =
  | 'dashboard' | 'room-reservation' | 'housekeeping' | 'billing'
  | 'restaurant' | 'guest' | 'employee' | 'inventory' | 'event' | 'spa' | 'auth';

interface Props {
  onNavigate: (s: Section) => void;
}

const CARDS: { key: Section; label: string; icon: string; desc: string; port?: number; ready?: boolean }[] = [
  { key: 'room-reservation', label: 'Room Reservation', icon: '🛏️', desc: 'Manage rooms, categories, features and reservations.', port: 8080 },
  { key: 'housekeeping',     label: 'Housekeeping',     icon: '🧹', desc: 'Create and track room cleaning & maintenance tasks.', port: 8086, ready: true },
  { key: 'billing',          label: 'Billing',          icon: '💳', desc: 'Handle invoices, payments and billing records.', port: 8083 },
  { key: 'restaurant',       label: 'Restaurant',       icon: '🍴', desc: 'Manage menu items, orders and tables.', port: 8084 },
  { key: 'guest',            label: 'Guest',            icon: '👤', desc: 'Guest profiles, check-in and check-out.', port: 8085 },
  { key: 'employee',         label: 'Employee',         icon: '👥', desc: 'Staff records, roles and shift management.', port: 8087 },
  { key: 'inventory',        label: 'Inventory',        icon: '📦', desc: 'Track stock, supplies and procurement.', port: 8088 },
  { key: 'event',            label: 'Events',           icon: '🎉', desc: 'Conference rooms, event bookings and catering.', port: 8089 },
  { key: 'spa',              label: 'Spa & Wellness',   icon: '💆', desc: 'Appointments, treatments and spa packages.', port: 8090 },
  { key: 'auth',             label: 'Auth / Users',     icon: '🔐', desc: 'User accounts, roles and authentication.', port: 8081 },
];

export default function Dashboard({ onNavigate }: Props) {
  return (
    <div className="db-page">
      {/* Hero */}
      <div className="db-hero">
        <div className="db-hero-text">
          <h1>🏨 Hotel Management System</h1>
          <p>Integrated platform for managing every aspect of your hotel operations.</p>
        </div>
        <div className="db-hero-stats">
          <div className="db-hero-stat">
            <span>10</span>
            <label>Services</label>
          </div>
          <div className="db-hero-stat">
            <span>1</span>
            <label>Live</label>
          </div>
          <div className="db-hero-stat">
            <span>React + Spring Boot</span>
            <label>Stack</label>
          </div>
        </div>
      </div>

      {/* Service grid */}
      <div className="db-section-title">Services</div>
      <div className="db-grid">
        {CARDS.map((c) => (
          <button key={c.key} className={`db-card ${c.ready ? 'db-card-ready' : ''}`} onClick={() => onNavigate(c.key)}>
            <div className="db-card-icon">{c.icon}</div>
            <div className="db-card-body">
              <div className="db-card-label">{c.label}</div>
              <div className="db-card-desc">{c.desc}</div>
              {c.port && <div className="db-card-port">:{c.port}</div>}
            </div>
            {c.ready && <span className="db-badge-live">LIVE</span>}
          </button>
        ))}
      </div>

      {/* Footer */}
      <div className="db-footer">DEA-20 · Hotel Management System · Spring Boot 3 · React 19 · TypeScript</div>
    </div>
  );
}
