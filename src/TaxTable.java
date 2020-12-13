import java.util.ArrayList;
import java.util.HashMap;

public class TaxTable{
    private double fixedCost = 0;
    private ArrayList<Double> propValueRates = new ArrayList<Double>();
    private ArrayList<Double> propValueRanges = new ArrayList<Double>();
    private HashMap<String, Double> locatoinCatAndRates = new HashMap<String, Double>();
    private double flatCharge = 0;
    private double penaltyRate = 0;

    TaxTable(double fixedCost, double flatCharge, double penaltyRate, ArrayList<String> locationCat,
            ArrayList<Double> locationRates, ArrayList<Double> valueRates, ArrayList<Double> valueRangeMaxs){
        setFixedCost(fixedCost);
        setPropValueRanges(valueRangeMaxs);
        setLocationCatAndRates(locationCat, locationRates);
        setFlatCharge(flatCharge);
        setPenaltyRate(penaltyRate);
        setPropValueRates(valueRates);
    }

    public void setFixedCost(double fixedCost){
        this.fixedCost = fixedCost;
    }

    public void setFlatCharge(double flatCharge){
        this.flatCharge = flatCharge;
    }

    public void setPenaltyRate(double rate){
        penaltyRate = rate;
    }

    public void setPropValueRates(ArrayList<Double> valueRates){
        this.propValueRates = valueRates;
    }

    public void setPropValueRanges(ArrayList<Double> valueRangeMaxs){
        this.propValueRanges = valueRangeMaxs;
    } 

    public void setLocationCatAndRates(ArrayList<String> locationCat, ArrayList<Double> locationRates){
        for(int i = 0; i < locationCat.size(); i ++){
            locatoinCatAndRates.put(locationCat.get(i), locationRates.get(i));
        }
    }
    public double getFixedCost(){
        return fixedCost;
    }

    public double getLocationRate(String propertyLocation){
        for(String k : locatoinCatAndRates.keySet()){
            if(propertyLocation.equalsIgnoreCase(k)){
                return locatoinCatAndRates.get(k);
            }
        }
        return -1.0; // if negative found then raise an error
    }

    public double getPropertyValueRate(String propertyValue){
        double propValue = Double.parseDouble(propertyValue);
        int ratePos = 0;
        for(int i = 0; i < propValueRanges.size(); i ++){
            if(propValue < propValueRanges.get(i)){
                ratePos = i;
                break;
            }
        }
        return propValueRates.get(ratePos);
    }

    public double getFlatCharge(){
        return flatCharge;
    }

    public double getPenaltyRate(){
        return penaltyRate;
    }
}