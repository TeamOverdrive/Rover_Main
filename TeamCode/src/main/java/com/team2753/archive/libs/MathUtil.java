package com.team2753.archive.libs;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 9/27/2018.
 * Copied from Relic_Main repo
 */

public class MathUtil {

    //Static class
    private MathUtil() {}

    /**
     * @param L1 Length One
     * @param L2 Length Two
     * @param xp X endpoint
     * @param yp Y endpoint
     * @return double array, first is theda1, second is theda2
     */
    public static double[] SolveInverseKinematics(double L1, double L2,
                                                  double xp, double yp){
        double a = Math.pow(xp, 2) + Math.pow(yp, 2);
        double b = Math.pow(L1, 2);
        double c = Math.pow(L2, 2);
        double d = a + b - c;
        double e = a- b - c;
        double f = 4*b;
        double g = f*a-Math.pow(d, 2);
        double h = f*c-Math.pow(e,2);
        double theda1 = Math.toDegrees(Math.atan2(xp, yp) - Math.atan2(Math.sqrt(g), d));
        double theda2 = Math.toDegrees(Math.atan2(Math.sqrt(h), e));


        return new double[]{
                theda1, theda2
        };
    }

    // Scale driver joystick input to make it easier to control a low speeds
    public static double scaleInput(double dVal)   {
        double[] scaleArray = {
                0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24, 0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00
                //to use a different scale, list alternate scale values here and comment out the line above
        };

        int index = (int) (dVal * 16.0);
        if (index < 0) {
            index = -index;
        } else if (index > 16)  {
            index = 16;
        }

        double dScale = 0.0;
        if (dVal < 0)  {
            dScale = -scaleArray[index];
        }  else {
            dScale = scaleArray[index];
        }

        return dScale;
    }

    static double scaleLift(double dVal)   {
        double[] scaleArray = {
                0.0, 0.05, 0.07, 0.10, 0.12, 0.15, 0.2, 0.3, 0.35, 0.4, 0.45, 0.50, 0.55, 0.6, 0.65, 0.7, 0.75
                //to use a different scale, list alternate scale values here and comment out the line above
        };

        int index = (int) (dVal * 16.0);
        if (index < 0) {
            index = -index;
        } else if (index > 16)  {
            index = 16;
        }

        double dScale = 0.0;
        if (dVal < 0)  {
            dScale = -scaleArray[index];
        }  else {
            dScale = scaleArray[index];
        }

        return dScale;
    }


    /**
     * f(x) = ax^3 + bx^2 + cx + d
     * @return roots of f
     */
    public static double[] solveCubic(double a, double b, double c, double d) {
        double[] result;
        if (a != 1) {
            b = b / a;
            c = c / a;
            d = d / a;
        }

        double p = c / 3 - b * b / 9;
        double q = b * b * b / 27 - b * c / 6 + d / 2;
        double D = p * p * p + q * q;

        if (Double.compare(D, 0) >= 0) {
            if (Double.compare(D, 0) == 0) {
                double r = Math.cbrt(-q);
                result = new double[2];
                result[0] = 2 * r;
                result[1] = -r;
            } else {
                double r = Math.cbrt(-q + Math.sqrt(D));
                double s = Math.cbrt(-q - Math.sqrt(D));
                result = new double[1];
                result[0] = r + s;
            }
        } else {
            double ang = Math.acos(-q / Math.sqrt(-p * p * p));
            double r = 2 * Math.sqrt(-p);
            result = new double[3];
            for (int k = -1; k <= 1; k++) {
                double theta = (ang - 2 * Math.PI * k) / 3;
                result[k + 1] = r * Math.cos(theta);
            }

        }
        for (int i = 0; i < result.length; i++) {
            result[i] = (result[i] - b / 3);
        }
        return result;
    }

    /**
     * f(x) = ax^2 + bx + c
     * @return roots of f
     */
    public static double[] solveQuadratic(double a, double b, double c) {
        double dis = Math.pow(b, 2) - (4.0 * a * c);
        if (dis < 0) {
            return new double[]{}; // no solutions
        }
        if (dis == 0) {
            double s = -b / (2.0 * a);
            return new double[]{s}; //one solution
        }
        dis = Math.sqrt(dis);
        double s1 = (-b + dis) / (2.0 * a);
        double s2 = (-b - dis) / (2.0 * a);
        return new double[]{s1, s2};
    }
}
