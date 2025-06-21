import React from 'react';
import { AppBar, Toolbar, Typography, Button, Box } from '@mui/material';
import CampaignIcon from '@mui/icons-material/Campaign';
import { useNavigate, useLocation } from 'react-router-dom';

const Header: React.FC = () => {
  const navigate = useNavigate();
  const location = useLocation();

  const isActive = (path: string) => {
    return location.pathname === path;
  };

  return (
    <AppBar position="static">
      <Toolbar>
        <Box display="flex" alignItems="center" gap={1} sx={{ flexGrow: 1 }}>
          <CampaignIcon />
          <Typography variant="h6" component="div">
            MediGestion
          </Typography>
        </Box>
        
        <Box display="flex" gap={1}>
          <Button
            color="inherit"
            onClick={() => navigate('/')}
            sx={{
              backgroundColor: isActive('/') ? 'rgba(255, 255, 255, 0.1)' : 'transparent'
            }}
          >
            Crear Campaña
          </Button>
          
          <Button
            color="inherit"
            onClick={() => navigate('/campanas')}
            sx={{
              backgroundColor: isActive('/campanas') ? 'rgba(255, 255, 255, 0.1)' : 'transparent'
            }}
          >
            Ver Campañas
          </Button>
          
          <Button
            color="inherit"
            onClick={() => navigate('/estados')}
            sx={{
              backgroundColor: isActive('/estados') ? 'rgba(255, 255, 255, 0.1)' : 'transparent'
            }}
          >
            Gestor de Estados
          </Button>
          
          <Button
            color="inherit"
            onClick={() => navigate('/editor')}
            sx={{
              backgroundColor: isActive('/editor') ? 'rgba(255, 255, 255, 0.1)' : 'transparent'
            }}
          >
            Editor
          </Button>
          
          <Button
            color="inherit"
            onClick={() => navigate('/historial')}
            sx={{
              backgroundColor: isActive('/historial') ? 'rgba(255, 255, 255, 0.1)' : 'transparent'
            }}
          >
            Historial
          </Button>
        </Box>
      </Toolbar>
    </AppBar>
  );
};

export default Header;