import React from 'react';
import './ComingSoon.css';

interface Props {
  section: string;
  icon: string;
  port?: number;
}

export default function ComingSoon({ section, icon, port }: Props) {
  return (
    <div className="cs-page">
      <div className="cs-card">
        <span className="cs-icon">{icon}</span>
        <h2>{section}</h2>
        <p className="cs-sub">This module is being developed by another team member.</p>
        {port && (
          <div className="cs-info">
            <span>Backend API</span>
            <a href={`http://localhost:${port}`} target="_blank" rel="noreferrer">
              http://localhost:{port}
            </a>
          </div>
        )}
        <div className="cs-badge">🚧 Coming Soon</div>
      </div>
    </div>
  );
}
