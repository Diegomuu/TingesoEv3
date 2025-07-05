import "./Home.css";
import Navbar from './Navbar';
import { Link } from 'react-router-dom';
import { useEffect } from 'react';

const Home = () => {
  useEffect(() => {
    // Crear efecto de partículas
    const createParticle = () => {
      const particles = document.querySelector('.particles');
      if (!particles) return;

      const particle = document.createElement('div');
      particle.className = 'particle';
      const size = Math.random() * 5 + 2;
      particle.style.width = `${size}px`;
      particle.style.height = `${size}px`;
      particle.style.left = `${Math.random() * 100}%`;
      particle.style.top = `${Math.random() * 100}%`;
      particles.appendChild(particle);

      setTimeout(() => {
        particle.remove();
      }, 15000);
    };

    const particleInterval = setInterval(createParticle, 2000);
    return () => clearInterval(particleInterval);
  }, []);

  return (
    <div className="page-wrapper">
      <Navbar />
      <div className="home-container">
        <div className="particles"></div>
        <div className="content-wrapper">
          <h1 className="home-title">
            <span className="icon">🏁</span>
            Bienvenido a SpeedKart!
          </h1>
          <p className="home-description">
            La mejor plataforma para gestionar carreras de karting, pilotos, tiempos y resultados. 
            Organiza eventos, revisa estadísticas y disfruta de la velocidad.
            <span className="icon">🚀</span>
          </p>
          <div className="home-buttons">
            <Link to="/register-pilot" className="btn-link">
              <button className="btn-primary">
                <span className="icon">👤</span>
                Registrar Piloto
              </button>
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Home;
