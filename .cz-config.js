module.exports = {
  types: [
    { value: "feat", name: "feat:     Nueva funcionalidad" },
    { value: "fix", name: "fix:      Corrección de errores" },
    { value: "chore", name: "chore:    Mantenimiento o configuración" },
    { value: "refactor", name: "refactor: Refactorización del código" },
    { value: "docs", name: "docs:     Cambios en la documentación" },
    { value: "test", name: "test:     Agregar o modificar tests" },
    { value: "style", name: "style:    Cambios de formato, comas, puntos, etc." },
    { value: "perf", name: "perf:     Mejora de rendimiento" },
  ],

  scopes: [{ name: "client" }, { name: "api" }, { name: "root" }],

  messages: {
    type: "Selecciona el tipo de cambio que estás realizando:",
    scope: "Selecciona el alcance del cambio (scope):",
    customScope: "Indica un alcance personalizado:",
    subject: "Escribe una breve descripción (imperativo, sin punto final):",
    body: "Agrega una descripción más larga (opcional):",
    breaking: "Lista los cambios importantes (BREAKING CHANGES):",
    footer: "Referencias de tickets (ej: #123):",
    confirmCommit: "¿Confirmas este commit?",
  },

  allowCustomScopes: true,
  allowBreakingChanges: ["feat", "fix"],
  skipQuestions: ["body", "breaking", "footer"],
  subjectLimit: 100,
};
