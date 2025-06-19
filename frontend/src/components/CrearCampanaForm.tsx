import React, { useState } from 'react';
import { Box, TextField, Button, Paper, Typography, MenuItem, InputAdornment, Divider } from '@mui/material';
import CampaignIcon from '@mui/icons-material/Campaign';
import DescriptionIcon from '@mui/icons-material/Description';
import EventIcon from '@mui/icons-material/Event';
import EventAvailableIcon from '@mui/icons-material/EventAvailable';
import ScienceIcon from '@mui/icons-material/Science';
import LocalOfferIcon from '@mui/icons-material/LocalOffer';
import GroupIcon from '@mui/icons-material/Group';
import TopicIcon from '@mui/icons-material/Topic';

const tiposExamen = [
  { value: '', label: 'Ninguno' },
  { value: 'COVID-19', label: 'COVID-19' },
  { value: 'SANGRE', label: 'Sangre' },
  { value: 'DIAGNOSTICO', label: 'Diagnóstico' },
];

const tiposPromocion = [
  { value: '', label: 'Ninguna' },
  { value: 'DESCUENTO', label: 'Descuento' },
  { value: 'NO_APLICA', label: 'No aplica' },
];

const gruposObjetivo = [
  { value: '', label: 'Ninguno' },
  { value: 'GENERAL', label: 'General' },
  { value: 'ADULTOS MAYORES', label: 'Adultos mayores' },
];

const temas = [
  { value: '', label: 'Ninguno' },
  { value: 'SALUD', label: 'Salud' },
  { value: 'PREVENCION', label: 'Prevención' },
  { value: 'VACUNACION', label: 'Vacunación' },
];

const CrearCampanaForm: React.FC = () => {
  const [form, setForm] = useState({
    nombre: '',
    descripcion: '',
    fechaInicio: '',
    fechaFin: '',
    tipoExamen: '',
    tipoPromocion: '',
    grupoObjetivo: '',
    tema: '',
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    // POST
    console.log(form);
  };

  return (
    <Paper sx={{ p: { xs: 2, md: 5 }, mt: 6, maxWidth: 600, mx: 'auto', borderRadius: 4 }}>
      <Box display="flex" alignItems="center" gap={2} mb={2}>
        <CampaignIcon color="primary" fontSize="large" />
        <Typography variant="h5" color="primary">
          Crear Campaña
        </Typography>
      </Box>
      <Divider sx={{ mb: 3 }} />
      <Box component="form" display="flex" flexDirection="column" gap={3} onSubmit={handleSubmit}>
        <TextField
          label="Nombre de campaña"
          name="nombre"
          value={form.nombre}
          onChange={handleChange}
          required
          InputProps={{
            startAdornment: (
              <InputAdornment position="start">
                <CampaignIcon color="primary" />
              </InputAdornment>
            ),
          }}
        />
        <TextField
          label="Descripción"
          name="descripcion"
          value={form.descripcion}
          onChange={handleChange}
          multiline
          minRows={2}
          InputProps={{
            startAdornment: (
              <InputAdornment position="start">
                <DescriptionIcon color="primary" />
              </InputAdornment>
            ),
          }}
        />
        <Box display="flex" gap={2}>
          <TextField
            label="Fecha de inicio"
            name="fechaInicio"
            type="date"
            value={form.fechaInicio}
            onChange={handleChange}
            InputLabelProps={{ shrink: true }}
            required
            fullWidth
            InputProps={{
              startAdornment: (
                <InputAdornment position="start">
                  <EventIcon color="primary" />
                </InputAdornment>
              ),
            }}
          />
          <TextField
            label="Fecha de fin"
            name="fechaFin"
            type="date"
            value={form.fechaFin}
            onChange={handleChange}
            InputLabelProps={{ shrink: true }}
            required
            fullWidth
            InputProps={{
              startAdornment: (
                <InputAdornment position="start">
                  <EventAvailableIcon color="primary" />
                </InputAdornment>
              ),
            }}
          />
        </Box>
        <TextField
          select
          label="Tipo de examen"
          name="tipoExamen"
          value={form.tipoExamen}
          onChange={handleChange}
          InputProps={{
            startAdornment: (
              <InputAdornment position="start">
                <ScienceIcon color="primary" />
              </InputAdornment>
            ),
          }}
        >
          {tiposExamen.map(option => (
            <MenuItem key={option.value} value={option.value}>{option.label}</MenuItem>
          ))}
        </TextField>
        <TextField
          select
          label="Tipo de promoción"
          name="tipoPromocion"
          value={form.tipoPromocion}
          onChange={handleChange}
          InputProps={{
            startAdornment: (
              <InputAdornment position="start">
                <LocalOfferIcon color="primary" />
              </InputAdornment>
            ),
          }}
        >
          {tiposPromocion.map(option => (
            <MenuItem key={option.value} value={option.value}>{option.label}</MenuItem>
          ))}
        </TextField>
        <TextField
          select
          label="Grupo objetivo"
          name="grupoObjetivo"
          value={form.grupoObjetivo}
          onChange={handleChange}
          InputProps={{
            startAdornment: (
              <InputAdornment position="start">
                <GroupIcon color="primary" />
              </InputAdornment>
            ),
          }}
        >
          {gruposObjetivo.map(option => (
            <MenuItem key={option.value} value={option.value}>{option.label}</MenuItem>
          ))}
        </TextField>
        <TextField
          select
          label="Tema"
          name="tema"
          value={form.tema}
          onChange={handleChange}
          InputProps={{
            startAdornment: (
              <InputAdornment position="start">
                <TopicIcon color="primary" />
              </InputAdornment>
            ),
          }}
        >
          {temas.map(option => (
            <MenuItem key={option.value} value={option.value}>{option.label}</MenuItem>
          ))}
        </TextField>
        <Button
          variant="contained"
          color="primary"
          type="submit"
          size="large"
          sx={{ mt: 2, borderRadius: 3, fontWeight: 600, letterSpacing: 1 }}
          startIcon={<CampaignIcon />}
        >
          Guardar
        </Button>
      </Box>
    </Paper>
  );
};

export default CrearCampanaForm;