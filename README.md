# AerolineaVS
Proyecto Aerolínea con Visual Studio Code + Maven + GitHub.

## Requisitos implementados
- CLI en Java para recomendar la tarifa más adecuada según reglas de negocio.
- Contrato `IPricingService` para consumir el cálculo sin depender de su implementación.
- Gestión con Maven.
- Tests con JUnit 5 y ejecución con Surefire.
- Cobertura con JaCoCo.
- Javadoc en código y generación vía Maven.

## Ejecutar tests
```bash
mvn test
```

## Generar informes de testing
```bash
mvn site:site
```

## Generar Javadoc
```bash
mvn javadoc:javadoc
```

## Ejecutar la CLI
```bash
mvn exec:java -Dexec.mainClass=com.aerolineavs.tarifas.App
```

## Suposiciones para evitar ambigüedades
- En la tarifa de estudiante, "al menos una vez al mes durante el curso" se interpreta como **9 viajes/año**.
- La frecuencia anual informada se usa como número de viajes relevantes para cada regla.
- Si no se cumple ninguna regla exacta, se devuelve "Sin tarifa aplicable".

## Estructura y extensión hacia una GUI
- `ClientePotencial` y `ResultadoTarifa` son los datos de entrada y salida del cálculo.
- `IPricingService` es el contrato que puede consumir la CLI, una GUI u otro adaptador.
- `ServicioTarifas` implementa las reglas de negocio y no depende de ninguna interfaz de usuario.
- `App` actúa como punto de composición de la CLI: crea la implementación y trabaja con el contrato.
- Una GUI puede recibir `IPricingService` por constructor o configuración, crear un `ClientePotencial`,
  llamar a `evaluar` y presentar `ResultadoTarifa`, sin invocar `App` ni conocer `ServicioTarifas`.
- `EvaluadorTarifas.evaluar` se conserva temporalmente como fachada obsoleta para no romper clientes
  existentes; el código nuevo debe depender de `IPricingService`.

### SOLID
- **DIP:** antes, `App` llamaba directamente al método estático de `EvaluadorTarifas`. Ahora el
  consumidor puede depender de `IPricingService`, aunque el cableado de la CLI elige la implementación
  concreta en el punto de entrada.
- **OCP:** la cadena de condiciones de `ServicioTarifas` debe modificarse al agregar reglas. Si las
  reglas crecen o cambian con frecuencia, el siguiente paso es extraer políticas/reglas independientes
  que se puedan registrar sin editar el selector central.
- **SRP:** `App` concentra lectura, conversión, errores y presentación de la CLI; esas funciones son
  una sola responsabilidad de adaptador, pero deberían separarse en componentes de presentación y
  validación al desarrollar la GUI. El servicio mantiene las reglas de selección.
- **ISP:** el contrato expone solo la operación de evaluación que necesitan los consumidores.
- **LSP:** no hay una infracción observable en el código actual; la interfaz habilita implementaciones
  sustituibles que deberán mantener el mismo contrato.
