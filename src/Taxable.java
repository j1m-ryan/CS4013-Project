public abstract class Taxable {
    // stores tax from 2000 onwards
    private double[] taxPerYear;

    public Taxable() {
        taxPerYear = new double[100];
    }

    public double getTaxInYear(int year) {
        return taxPerYear[year];
    }

    public void setTaxInYear(int year, double value) {
        taxPerYear[year] = value;
    }

    public double getTotalTaxDue() {
        double total = 0;
        for (double t : taxPerYear) {
            total += t;
        }
        return total;
    }
}
