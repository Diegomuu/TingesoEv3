import React from "react";
import { Link } from "react-router-dom";
import "../assets/styles.css";

const Navbar = () => {
  return (
    <nav className="navbar">
      <ul>
        <li><Link to="/"><span className="icon">🏠</span> Inicio</Link></li>
        <li><Link to="/reportes"><span className="icon">📊</span> Reportes</Link></li>
        <li><Link to="/rack-semanal"><span className="icon">🏆</span> Rack Semanal</Link></li>
      </ul>
    </nav>
  );
};

export default Navbar;