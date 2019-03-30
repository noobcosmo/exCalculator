class Calculator {
    private double num0;
    private int opr;
    private double num1;
    double result;

    Calculator(double num0, int opr, double num1) {
        if (opr > 3) {
            System.out.println("Error! ");
            System.exit(-4);
        }
        this.num0 = num0;
        this.opr = opr;
        this.num1 = num1;
        this.result = this.calculate();
    }

    Calculator(double num0, int opr) {
        if (opr > 5) {
            System.out.println("Error! ");
            System.exit(-6);
        }
        this.num0 = num0;
        this.opr = opr;
        this.num1 = num0;
        this.result = this.calculate();
    }

    private double calculate() {
        double inter = 0;
        switch (this.opr) {
            case 0:
                inter = add();
                break;
            case 1:
                inter = sub();
                break;
            case 2:
                inter = mul();
                break;
            case 3:
                inter = div();
                break;
            case 4:
                inter = sqr();
                break;
            case 5:
                inter = sqrRoot();
                break;
            default:
                System.out.println("Error! ");
                System.exit(-2);
                break;
        }
        return inter;
    }

    private double add() {
        return num0 + num1;
    }

    private double sub() {
        return num0 - num1;
    }

    private double mul() {
        return num0 * num1;
    }

    private double div() {
        return num0 / num1;
    }

    private double sqr() {
        return num0 * num0;
    }

    private double sqrRoot() {
        return Math.sqrt(num0);
    }
}
