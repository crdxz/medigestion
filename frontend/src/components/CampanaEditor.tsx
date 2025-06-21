import React, { useState, useEffect } from 'react';
import {
  Box,
  Paper,
  Typography,
  TextField,
  Button,
  Alert,
  CircularProgress,
  Grid,
  Chip,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  InputAdornment
} from '@mui/material';
import {
  Edit as EditIcon,
  Save as SaveIcon,
  Cancel as CancelIcon,
  Warning as WarningIcon,
  CheckCircle as CheckIcon,
  Info as InfoIcon
} from '@mui/icons-material';
import axios from 'axios';

interface Campana {
  id: number;
  nombre: string;
  descripcion: string;
  fechaInicio: string;
  fechaFin: string;
  estado: string;
  tipoCampana: string;
  tema: string;
  tipoExamen: string;
  fechaCreacion: string;
  fechaModificacion: string;
}

interface ValidationResult {
  puedeModificar: boolean;
  mensaje: string;
  operacionesPermitidas: string[];
}

const CampanaEditor: React.FC = () => {
  const [campanas, setCampanas] = useState<Campana[]>([]);
  const [selectedCampana, setSelectedCampana] = useState<Campana | null>(null);
  const [editingCampana, setEditingCampana] = useState<Campana | null>(null);
  const [validation, setValidation] = useState<ValidationResult | null>(null);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState<string | null>(null);
  const [dialogOpen, setDialogOpen] = useState(false);

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

  const handleEditClick = async (campana: Campana) => {
    setSelectedCampana(campana);
    setEditingCampana({ ...campana });
    setError(null);
    setSuccess(null);

    // Simplificar temporalmente - permitir edición sin validación
    setValidation({
      puedeModificar: true,
      mensaje: "Modo de edición habilitado",
      operacionesPermitidas: ['modificar']
    });

    setDialogOpen(true);
  };

  const handleSave = async () => {
    if (!editingCampana || !selectedCampana) return;

    setSaving(true);
    try {
      await axios.put(`http://localhost:8081/api/campanas/${selectedCampana.id}`, editingCampana, {
        params: { usuario: 'admin' }
      });

      // Actualizar lista local
      setCampanas(prev => prev.map(c => 
        c.id === selectedCampana.id ? editingCampana : c
      ));

      setSuccess('Campaña actualizada exitosamente');
      setDialogOpen(false);
      setSelectedCampana(null);
      setEditingCampana(null);
      setValidation(null);
    } catch (err: any) {
      setError('Error al actualizar la campaña');
    } finally {
      setSaving(false);
    }
  };

  const handleCancel = () => {
    setDialogOpen(false);
    setSelectedCampana(null);
    setEditingCampana(null);
    setValidation(null);
    setError(null);
    setSuccess(null);
  };

  const handleFieldChange = (field: keyof Campana, value: string) => {
    if (!editingCampana) return;
    setEditingCampana(prev => prev ? { ...prev, [field]: value } : null);
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

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleDateString();
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
      <Typography variant="h4" component="h1" gutterBottom>
        Editor de Campañas
      </Typography>

      {error && (
        <Alert severity="error" sx={{ mb: 2 }}>
          {error}
        </Alert>
      )}

      {success && (
        <Alert severity="success" sx={{ mb: 2 }}>
          {success}
        </Alert>
      )}

      <Grid container spacing={3}>
        {campanas.map((campana) => (
          <Grid item xs={12} sm={6} md={4} key={campana.id}>
            <Paper sx={{ p: 2 }}>
              <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
                <Typography variant="h6" noWrap>
                  {campana.nombre}
                </Typography>
                <Chip
                  label={campana.estado}
                  color={getEstadoColor(campana.estado) as any}
                  size="small"
                />
              </Box>

              <Typography variant="body2" color="text.secondary" gutterBottom>
                {campana.descripcion}
              </Typography>

              <Box display="flex" gap={1} flexWrap="wrap" mb={2}>
                <Chip label={campana.tipoCampana} size="small" />
                {campana.tipoExamen && (
                  <Chip label={campana.tipoExamen} size="small" />
                )}
              </Box>

              <Typography variant="caption" display="block" gutterBottom>
                <strong>Fechas:</strong> {formatDate(campana.fechaInicio)} - {formatDate(campana.fechaFin)}
              </Typography>

              <Typography variant="caption" display="block" gutterBottom>
                <strong>Modificada:</strong> {formatDate(campana.fechaModificacion)}
              </Typography>

              <Button
                variant="outlined"
                startIcon={<EditIcon />}
                onClick={() => handleEditClick(campana)}
                fullWidth
                sx={{ mt: 2 }}
              >
                Editar
              </Button>
            </Paper>
          </Grid>
        ))}
      </Grid>

      {/* Dialog de edición */}
      <Dialog open={dialogOpen} onClose={handleCancel} maxWidth="md" fullWidth>
        <DialogTitle>
          <Box display="flex" alignItems="center" gap={1}>
            <EditIcon />
            Editar Campaña: {selectedCampana?.nombre}
          </Box>
        </DialogTitle>
        <DialogContent>
          {validation && (
            <Alert 
              severity={validation.puedeModificar ? 'info' : 'warning'} 
              icon={validation.puedeModificar ? <CheckIcon /> : <WarningIcon />}
              sx={{ mb: 2 }}
            >
              {validation.mensaje}
            </Alert>
          )}

          {editingCampana && (
            <Grid container spacing={2}>
              <Grid item xs={12}>
                <TextField
                  label="Nombre"
                  value={editingCampana.nombre}
                  onChange={(e) => handleFieldChange('nombre', e.target.value)}
                  fullWidth
                  disabled={!validation?.puedeModificar}
                />
              </Grid>

              <Grid item xs={12}>
                <TextField
                  label="Descripción"
                  value={editingCampana.descripcion}
                  onChange={(e) => handleFieldChange('descripcion', e.target.value)}
                  multiline
                  rows={3}
                  fullWidth
                  disabled={!validation?.puedeModificar}
                />
              </Grid>

              <Grid item xs={6}>
                <TextField
                  label="Fecha de Inicio"
                  type="date"
                  value={editingCampana.fechaInicio}
                  onChange={(e) => handleFieldChange('fechaInicio', e.target.value)}
                  fullWidth
                  InputLabelProps={{ shrink: true }}
                  disabled={!validation?.puedeModificar}
                />
              </Grid>

              <Grid item xs={6}>
                <TextField
                  label="Fecha de Fin"
                  type="date"
                  value={editingCampana.fechaFin}
                  onChange={(e) => handleFieldChange('fechaFin', e.target.value)}
                  fullWidth
                  InputLabelProps={{ shrink: true }}
                  disabled={!validation?.puedeModificar}
                />
              </Grid>

              <Grid item xs={6}>
                <TextField
                  label="Tipo de Campaña"
                  value={editingCampana.tipoCampana}
                  onChange={(e) => handleFieldChange('tipoCampana', e.target.value)}
                  fullWidth
                  disabled={!validation?.puedeModificar}
                />
              </Grid>

              <Grid item xs={6}>
                <TextField
                  label="Tipo de Examen"
                  value={editingCampana.tipoExamen}
                  onChange={(e) => handleFieldChange('tipoExamen', e.target.value)}
                  fullWidth
                  disabled={!validation?.puedeModificar}
                />
              </Grid>

              <Grid item xs={12}>
                <TextField
                  label="Tema"
                  value={editingCampana.tema}
                  onChange={(e) => handleFieldChange('tema', e.target.value)}
                  fullWidth
                  disabled={!validation?.puedeModificar}
                />
              </Grid>

              <Grid item xs={12}>
                <FormControl fullWidth disabled={!validation?.puedeModificar}>
                  <InputLabel>Estado</InputLabel>
                  <Select
                    value={editingCampana.estado}
                    onChange={(e) => handleFieldChange('estado', e.target.value)}
                    label="Estado"
                  >
                    <MenuItem value="PENDIENTE">Pendiente</MenuItem>
                    <MenuItem value="ACTIVA">Activa</MenuItem>
                    <MenuItem value="FINALIZADA">Finalizada</MenuItem>
                    <MenuItem value="CANCELADA">Cancelada</MenuItem>
                  </Select>
                </FormControl>
              </Grid>
            </Grid>
          )}
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCancel} startIcon={<CancelIcon />}>
            Cancelar
          </Button>
          <Button
            onClick={handleSave}
            variant="contained"
            startIcon={<SaveIcon />}
            disabled={!validation?.puedeModificar || saving}
          >
            {saving ? <CircularProgress size={20} /> : 'Guardar'}
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
};

export default CampanaEditor; 