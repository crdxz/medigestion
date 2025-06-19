import React from 'react';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';

interface HeaderProps {
  onSelect: (view: 'inicio' | 'crear') => void;
  selected: 'inicio' | 'crear';
}

const Header: React.FC<HeaderProps> = ({ onSelect, selected }) => (
  <AppBar position="static">
    <Toolbar>
      <Typography variant="h6" sx={{ flexGrow: 1 }}>
        MediGestions
      </Typography>
      <Box>
        <Button 
          color={selected === 'inicio' ? 'secondary' : 'inherit'} 
          onClick={() => onSelect('inicio')}
        >
          Inicio
        </Button>
        <Button 
          color={selected === 'crear' ? 'secondary' : 'inherit'} 
          onClick={() => onSelect('crear')}
        >
          Crear campaña
        </Button>
      </Box>
    </Toolbar>
  </AppBar>
);

export default Header;