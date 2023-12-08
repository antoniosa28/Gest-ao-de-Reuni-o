import java.util.List;

public class ReuniaoVirtual extends Evento {
    private List<String> equipamentos;

    public ReuniaoVirtual(String descricao, String data, List<String> equipamentos) {
        super(descricao, data);
        this.equipamentos = equipamentos;
    }

    @Override
    public double calcularCusto() {
        // Implementação do cálculo de custo para Reunião Virtual
        double custoTotal = 0.0;

        for (String equipamento : equipamentos) {
            if (equipamento.equals("Camera")) {
                custoTotal += 50.0;
            } else if (equipamento.equals("Microfone")) {
                custoTotal += 30.0;
            }
        }
        return custoTotal;
    }
}