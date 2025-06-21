import React, { useState, useEffect } from 'react';
import {
  Box,
  Paper,
  Typography,
  Chip,
  Button,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Alert,
  CircularProgress,
  Grid,
  Card,
  CardContent,
  IconButton,
  Tooltip
} from '@mui/material';
import {
  PlayArrow as ActivarIcon,
  Stop as FinalizarIcon,
  Cancel as CancelarIcon,
  Refresh as PendienteIcon,
  Info as InfoIcon,
  History as HistoryIcon
} from '@mui/icons-material';
import axios from 'axios';

interface CampanaState {
  id: number;
  nombre: string;
  estado: string;
  fechaCreacion: string;
  fechaModificacion: string;
}

interface StateInfo {
  mensaje: string;
  puedeModificar: boolean;
  puedeEliminar: boolean;
  puedeActivar: boolean;
}

const CampanaStateManager: React.FC = () => {
  const [campanas, setCampanas] = useState<CampanaState[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [selectedCampana, setSelectedCampana] = useState<CampanaState | null>(null);
  const [stateInfo, setStateInfo] = useState<StateInfo | null>(null);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [actionLoading, setActionLoading] = useState(false);

  useEffect(() => {
    fetchCampanas();
  }, []);

  const fetchCampanas = async () => {
    try {
      const response = await axios.get('http://localhost:8081/api/campanas');
      setCampanas(response.data);
      setLoading(false);
    } catch (err: any) {
      setError('Error al cargar las campañas');
      setLoading(false);
    }
  };

  const getEstadoColor = (estado: string) => {
    switch (estado) {
      case 'PENDIENTE': return 'warning';
      case 'ACTIVA': return 'success';
      case 'FINALIZADA': return 'info';
      case 'CANCELADA': return 'error';
      default: return 'default';
    }
  };

  const getEstadoIcon = (estado: string) => {
    switch (estado) {
      case 'PENDIENTE': return <PendienteIcon />;
      case 'ACTIVA': return <ActivarIcon />;
      case 'FINALIZADA': return <FinalizarIcon />;
      case 'CANCELADA': return <CancelarIcon />;
      default: return <InfoIcon />;
    }
  };

  const handleStateInfo = async (campana: CampanaState) => {
    try {
      // Simplificar temporalmente - usar datos simulados
      setStateInfo({
        mensaje: `La campaña está en estado ${campana.estado}`,
        puedeModificar: campana.estado !== 'FINALIZADA' && campana.estado !== 'CANCELADA',
        puedeEliminar: campana.estado === 'CANCELADA',
        puedeActivar: campana.estado === 'PENDIENTE'
      });
      setSelectedCampana(campana);
      setDialogOpen(true);
    } catch (err: any) {
      setError('Error al obtener información del estado');
    }
  };

  const handleStateChange = async (campanaId: number, newState: string) => {
    setActionLoading(true);
    try {
      // Simular cambio de estado (en un caso real, esto sería un endpoint específico)
      const campanaToUpdate = campanas.find(c => c.id === campanaId);
      if (!campanaToUpdate) return;

      const updatedCampana = { ...campanaToUpdate, estado: newState };
      
      await axios.put(`http://localhost:8081/api/campanas/${campanaId}`, updatedCampana, {
        params: { usuario: 'admin' }
      });

      // Actualizar lista local
      setCampanas(prev => prev.map(c => 
        c.id === campanaId ? { ...c, estado: newState } : c
      ));

      setDialogOpen(false);
      setStateInfo(null);
    } catch (err: any) {
      setError('Error al cambiar el estado de la campaña');
    } finally {
      setActionLoading(false);
    }
  };

  const handleUndo = async () => {
    setActionLoading(true);
    try {
      await axios.post('http://localhost:8081/api/campanas/undo');
      await fetchCampanas(); // Recargar datos
      setError(null);
    } catch (err: any) {
      setError('Error al deshacer la última operación');
    } finally {
      setActionLoading(false);
    }
  };

  if (loading) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" minHeight="400px">
        <CircularProgress />
      </Box>
    );
  }

  return (
    <Box sx={{ p: 3 }}>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={3}>
        <Typography variant="h4" component="h1">
          Gestor de Estados de Campañas
        </Typography>
        <Button
          variant="outlined"
          startIcon={<HistoryIcon />}
          onClick={handleUndo}
          disabled={actionLoading}
        >
          Deshacer Última Operación
        </Button>
      </Box>

      {error && (
        <Alert severity="error" sx={{ mb: 2 }}>
          {error}
        </Alert>
      )}

      <Grid container spacing={3}>
        {campanas.map((campana) => (
          <Grid item xs={12} sm={6} md={4} key={campana.id}>
            <Card>
              <CardContent>
                <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
                  <Typography variant="h6" noWrap>
                    {campana.nombre}
                  </Typography>
                  <Tooltip title="Ver información del estado">
                    <IconButton
                      size="small"
                      onClick={() => handleStateInfo(campana)}
                    >
                      <InfoIcon />
                    </IconButton>
                  </Tooltip>
                </Box>

                <Chip
                  icon={getEstadoIcon(campana.estado)}
                  label={campana.estado}
                  color={getEstadoColor(campana.estado) as any}
                  sx={{ mb: 2 }}
                />

                <Typography variant="body2" color="text.secondary" gutterBottom>
                  Creada: {new Date(campana.fechaCreacion).toLocaleDateString()}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  Modificada: {new Date(campana.fechaModificacion).toLocaleDateString()}
                </Typography>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>

      {/* Dialog para información del estado */}
      <Dialog open={dialogOpen} onClose={() => setDialogOpen(false)} maxWidth="sm" fullWidth>
        <DialogTitle>
          Información del Estado - {selectedCampana?.nombre}
        </DialogTitle>
        <DialogContent>
          {stateInfo && (
            <Box>
              <Alert severity="info" sx={{ mb: 2 }}>
                {stateInfo.mensaje}
              </Alert>
              
              <Typography variant="h6" gutterBottom>
                Operaciones Permitidas:
              </Typography>
              
              <Box display="flex" gap={1} flexWrap="wrap" mb={2}>
                <Chip
                  label="Modificar"
                  color={stateInfo.puedeModificar ? 'success' : 'default'}
                  variant={stateInfo.puedeModificar ? 'filled' : 'outlined'}
                />
                <Chip
                  label="Eliminar"
                  color={stateInfo.puedeEliminar ? 'success' : 'default'}
                  variant={stateInfo.puedeEliminar ? 'filled' : 'outlined'}
                />
                <Chip
                  label="Activar"
                  color={stateInfo.puedeActivar ? 'success' : 'default'}
                  variant={stateInfo.puedeActivar ? 'filled' : 'outlined'}
                />
              </Box>

              <Typography variant="h6" gutterBottom>
                Cambiar Estado:
              </Typography>
              
              <Box display="flex" gap={1} flexWrap="wrap">
                {selectedCampana?.estado !== 'ACTIVA' && (
                  <Button
                    variant="contained"
                    color="success"
                    startIcon={<ActivarIcon />}
                    onClick={() => handleStateChange(selectedCampana!.id, 'ACTIVA')}
                    disabled={actionLoading}
                  >
                    Activar
                  </Button>
                )}
                
                {selectedCampana?.estado !== 'FINALIZADA' && (
                  <Button
                    variant="contained"
                    color="info"
                    startIcon={<FinalizarIcon />}
                    onClick={() => handleStateChange(selectedCampana!.id, 'FINALIZADA')}
                    disabled={actionLoading}
                  >
                    Finalizar
                  </Button>
                )}
                
                {selectedCampana?.estado !== 'CANCELADA' && (
                  <Button
                    variant="contained"
                    color="error"
                    startIcon={<CancelarIcon />}
                    onClick={() => handleStateChange(selectedCampana!.id, 'CANCELADA')}
                    disabled={actionLoading}
                  >
                    Cancelar
                  </Button>
                )}
                
                {selectedCampana?.estado !== 'PENDIENTE' && (
                  <Button
                    variant="contained"
                    color="warning"
                    startIcon={<PendienteIcon />}
                    onClick={() => handleStateChange(selectedCampana!.id, 'PENDIENTE')}
                    disabled={actionLoading}
                  >
                    Poner en Pendiente
                  </Button>
                )}
              </Box>
            </Box>
          )}
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setDialogOpen(false)}>
            Cerrar
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
};

export default CampanaStateManager; 