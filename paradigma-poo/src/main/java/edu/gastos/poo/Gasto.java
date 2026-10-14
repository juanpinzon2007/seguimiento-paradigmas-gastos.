package main.java.edu.gastos.poo;

public class Gasto {
    private String concepto;
    private double monto;
    private String categoria;

    public Gasto(String concepto, double monto, String categoria) {
        this.concepto = concepto;
        this.monto = monto;
        this.categoria = categoria;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return String.format("Gasto [Concepto: %s, Monto: $%.2f, Categoría: %s]", concepto, monto, categoria);
    }
}