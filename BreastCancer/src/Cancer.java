import java.util.List;

public class Cancer {

	  
/***	  
 *  
 * 1. Sample code number            id number
 * 2. Clump Thickness               1 - 10
 * 3. Uniformity of Cell Size       1 - 10
 * 4. Uniformity of Cell Shape      1 - 10
 * 5. Marginal Adhesion             1 - 10
 * 6. Single Epithelial Cell Size   1 - 10
 * 7. Bare Nuclei                   1 - 10
 * 8. Bland Chromatin               1 - 10
 * 9. Normal Nucleoli               1 - 10
 * 10. Mitoses                      1 - 10
 * 11. Class:						2 - Benign/4 - Malignant
 * 	  
***/
	
	private Double SCN;
	private List<Double> predictors;
	private Double classY;
	
	
	
	/**
	 * @return the sCN
	 */
	public Double getSCN() {
		return SCN;
	}
	
	/**
	 * @param double1 the sCN to set
	 */
	public void setSCN(Double double1) {
		SCN = double1;
	}
	
	/**
	 * @return the predictors
	 */
	public List<Double> getPredictors() {
		return predictors;
	}
	
	/**
	 * @param predictors the predictors to set
	 */
	public void setPredictors(List<Double> predictors) {
		this.predictors = predictors;
	}
	
	/**
	 * @return the classY
	 */
	public Double getClassY() {
		return classY;
	}
	
	/**
	 * @param classY the classY to set
	 */
	public void setClassY(Double classY) {
		this.classY = classY;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Cancer [SCN=" + SCN + "/nPredictors= " + predictors + "\nClass= " + classY + " ]";
	}
	
	/*
	 * converts object type cancer to CSV format
	 */
	public String toCSV() {
		String csv = "" + SCN;
		for(Double p : predictors)
			csv = csv + "," + p;
		csv = csv + "," + classY;
		return csv;
	}
	
}
