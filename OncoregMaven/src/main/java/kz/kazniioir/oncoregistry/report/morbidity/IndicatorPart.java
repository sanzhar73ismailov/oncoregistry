package kz.kazniioir.oncoregistry.report.morbidity;

public class IndicatorPart {

    private int valueAbsolute;
    private int valuePopulation;
    private double valueOn100_000Pop;
    private int baseOfDivision;

    public IndicatorPart(int baseOfDivision) {
        this.baseOfDivision = baseOfDivision;
    }

    public int getValueAbsolute() {
        return valueAbsolute;
    }

    public void setValueAbsolute(int valueAbsolute) {
        this.valueAbsolute = valueAbsolute;
    }

    public int getValuePopulation() {
        return valuePopulation;
    }

    public void setValuePopulation(int valuePopulation) {
        this.valuePopulation = valuePopulation;
    }

    public double getValueOn100_000Pop() {
        return valueOn100_000Pop;
    }

    public void setValueOn100_000Pop(double valueOn100_000Pop) {
        this.valueOn100_000Pop = valueOn100_000Pop;
    }

    public void setValueOn100_000PopByFormula() {
        if (valueAbsolute > 0 && valuePopulation > 0) {
            //this.valueOn100_000Pop = ((double) this.valueAbsolute) / ((double) this.valuePopulation) * MorbidityReport.COEF_MORBIDITY;
            this.valueOn100_000Pop = ((double) this.valueAbsolute) / ((double) this.valuePopulation) * baseOfDivision;
        }
    }

    @Override
    public String toString() {
        return valueAbsolute + "(" + String.format("%.2f", valueOn100_000Pop) + ")";
    }
}
