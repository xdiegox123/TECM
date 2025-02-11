package src.core.utils.dtos;

/**
 * Data Transfer Object (DTO) class for storing the regression parameters,
 * including b0, b1, the coefficient of correlation (cof),
 * and the coefficient of determination (cod).
 */
public class RegressionParams {
    private float bZero = 0.0f;
    private float bOne = 0.0f;
    private float cof;
    private float cod;

    public float getbZero() {
        return bZero;
    }

    public void setbZero(float bZero) {
        this.bZero = bZero;
    }

    public float getbOne() {
        return bOne;
    }

    public void setbOne(float bOne) {
        this.bOne = bOne;
    }

    public float getCof() {
        return cof;
    }

    public void setCof(float cof) {
        this.cof = cof;
    }

    public float getCod() {
        return cod;
    }

    public void setCod(float cod) {
        this.cod = cod;
    }
}
