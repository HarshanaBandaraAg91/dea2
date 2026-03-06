import React, { useState } from 'react';
import './App.css';
import HousekeepingPage from './components/HousekeepingPage';
import Dashboard from './components/Dashboard';
import ComingSoon from './components/ComingSoon';

type Section =
  | 'dashboard'
  | 'room-reservation'
  | 'housekeeping'
  | 'billing'
  | 'restaurant'
  | 'guest'
  | 'employee'
  | 'inventory'
  | 'event'
  | 'spa'
  | 'auth';

const NAV_ITEMS: { key: Section; label: string; icon: string; port?: number }[] = [
  { key: 'dashboard',        label: 'Dashboard',         icon: '🏨' },
  { key: 'room-reservation', label: 'Room Reservation',  icon: '🛏️', port: 8080 },
  { key: 'housekeeping',     label: 'Housekeeping',      icon: '🧹', port: 8086 },
  { key: 'billing',          label: 'Billing',           icon: '💳', port: 8083 },
  { key: 'restaurant',       label: 'Restaurant',        icon: '🍴', port: 8084 },
  { key: 'guest',            label: 'Guest',             icon: '👤', port: 8085 },
  { key: 'employee',         label: 'Employee',          icon: '👥', port: 8087 },
  { key: 'inventory',        label: 'Inventory',         icon: '📦', port: 8088 },
  { key: 'event',            label: 'Events',            icon: '🎉', port: 8089 },
  { key: 'spa',              label: 'Spa & Wellness',    icon: '💆', port: 8090 },
  { key: 'auth',             label: 'Auth / Users',      icon: '🔐', port: 8081 },
];

function App() {
  const [active, setActive] = useState<Section>('dashboard');
  const [sidebarOpen, setSidebarOpen] = useState(true);

  const current = NAV_ITEMS.find((n) => n.key === active)!;

  const renderPage = () => {
    if (active === 'dashboard')    return <Dashboard onNavigate={setActive} />;
    if (active === 'housekeeping') return <HousekeepingPage />;
    return <ComingSoon section={current.label} icon={current.icon} port={current.port} />;
  };

  return (
    <div className={`hms-app ${sidebarOpen ? 'sidebar-open' : 'sidebar-closed'}`}>
      {/* Sidebar */}
      <aside className="hms-sidebar">
        <div className="hms-sidebar-brand">
          <span className="brand-icon">🏨</span>
          {sidebarOpen && <span className="brand-text">HMS</span>}
        </div>
        <nav className="hms-nav">
          {NAV_ITEMS.map((item) => (
            <button
              key={item.key}
              className={`hms-nav-item ${active === item.key ? 'active' : ''}`}
              onClick={() => setActive(item.key)}
              title={item.label}
            >
              <span className="nav-icon">{item.icon}</span>
              {sidebarOpen && <span className="nav-label">{item.label}</span>}
            </button>
          ))}
        </nav>
        <button className="hms-sidebar-toggle" onClick={() => setSidebarOpen(!sidebarOpen)}>
          {sidebarOpen ? '◀' : '▶'}
        </button>
      </aside>

      {/* Main content */}
      <main className="hms-main">
        {renderPage()}
      </main>
    </div>
  );
}

export default App;

