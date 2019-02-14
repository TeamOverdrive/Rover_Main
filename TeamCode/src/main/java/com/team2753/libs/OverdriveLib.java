package com.team2753.libs;

/**
 * Used to store math and things
 * Created by joshua9889 on 12/10/2017.
 */

public class OverdriveLib {

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
}
