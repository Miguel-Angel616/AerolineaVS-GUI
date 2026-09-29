package com.aerolineavs.tarifas;

/**
 * Fachada de compatibilidad para clientes anteriores a {@link IPricingService}.
 *
 * @deprecated Inyecte {@link IPricingService} y use {@link ServicioTarifas} como
 * implementación predeterminada.
 */
@Deprecated(forRemoval = false)
public final class EvaluadorTarifas {

    private static final IPricingService SERVICIO_PREDETERMINADO = new ServicioTarifas();

    private EvaluadorTarifas() {
    }

    /**
     * Evalúa una tarifa mediante la implementación predeterminada.
     *
     * @param cliente datos del cliente
     * @return tarifa resultante junto con suposiciones
     * @deprecated Inyecte y llame a {@link IPricingService#evaluar(ClientePotencial)}.
     */
    @Deprecated(forRemoval = false)
    public static ResultadoTarifa evaluar(ClientePotencial cliente) {
        return SERVICIO_PREDETERMINADO.evaluar(cliente);
    }
}
