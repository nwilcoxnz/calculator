package com.example.nicolewilcox.calculator;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    ArrayList <String> arrayList = new ArrayList<String>();

    String calculationString = ""; // To hold what is to be displayed in the calculation (upper) field
    String string2 = ""; // To hold what has just been entered, and will be added to string
    int total = 0; // Result of the calculation(s)
    int entered = 0; // Integer version of the string that user has entered

    public void onClick (View v){ // What happens when you click buttons 0 - 9, "+', "-", "/" or "*"

        TextView textResult = (TextView) findViewById(R.id.textCalculation); // Calculation field
        Button button = (Button) v;
        calculationString = (String) button.getText().toString();

        if (!calculationString.contains("+") && !calculationString.contains("-") && !calculationString.contains("/") && !calculationString.contains("*")){
            entered = Integer.parseInt(string2 + calculationString);
            if(entered < 999999999) { // Make sure the input isn't exceeding the value of an integer
                string2 = string2 + calculationString;
            }
            if (!arrayList.isEmpty()) { // Remove the value that was last entered so it can be replaced
                arrayList.remove(arrayList.size() - 1);
            }
            System.out.println("This is what's being added: " + string2);
            arrayList.add(string2);

        }

        else {
            arrayList.add(calculationString);
            arrayList.add(calculationString);
            string2 = ""; // Clear the calculation string
        }

        textResult.setText(textResult.getText().toString() + calculationString); // Display the result text

    }

    public void onClickEquals (View v){ // What happens when you press "="

        TextView textResult = (TextView) findViewById(R.id.textResult); // Result field
        TextView textError = (TextView) findViewById(R.id.textError); // Error field
        Button button = (Button) v;
        String string = (String) button.getText().toString();
        boolean isValid = false; // True if the calculation has a valid structure
        TextView textCalculation = (TextView) findViewById(R.id.textCalculation); // Calculation field

        int numLoops = arrayList.size();
        System.out.println("ARRAY SIZE: " + arrayList.size());
        System.out.println("NUM LOOPS: " + numLoops);

        total = 0; // Reset total

        if (!arrayList.isEmpty()) { // If a calculation has been entered

            for (int i = 0; i < arrayList.size(); i++) { // Check that the syntactic structure of the expression is correct
                if (arrayList.get(i).equals("+") || arrayList.get(i).equals("-") || arrayList.get(i).equals("/") || arrayList.get(i).equals("*")) {// If we are looking at a symbol then the next character must not be a symbol
                    if (arrayList.get(i + 1).equals("+") || arrayList.get(i + 1).equals("-") || arrayList.get(i + 1).equals("/") || arrayList.get(i + 1).equals("*")) {
                        textError.setText("Invalid operation");
                        break;
                    }
                }
                isValid = true;
            }

            if (numLoops == 1) { // If you've only entered one number or symbol
                // Make sure the data entered is a number and not a symbol
                if (arrayList.get(0).equals("+") || arrayList.get(0).equals("-") || arrayList.get(0).equals("/") || arrayList.get(0).equals("*")) {
                    textError.setText("Invalid operation");
                } else {
                    total = Integer.parseInt(arrayList.get(0));
                }
                textResult.setText(Integer.toString(total)); // Display the result
            }
            else {
                while (numLoops != 1 && isValid == true) { // Complete the calculation

                    if (numLoops == 2) { // If the user enters only one number and one operand (in that order) e.g. 2 +
                        if (arrayList.get(1).equals("+") || arrayList.get(1).equals("-") || arrayList.get(1).equals("/") || arrayList.get(1).equals("*")) {// If we are looking at a symbol then the next character must not be a symbol
                            textError.setText("Invalid operation");
                            break;
                        }
                        else{ // The user enters one operand and one number e.g. + 2
                           continue;
                        }
                    }

                    if (numLoops > 3) { // If you are doing more than one calculation

                        if (arrayList.get(3).contains("*") || arrayList.get(3).contains("/")) { // Calculate multiplication and division in the correct order by evaluating the second calculation

                            arrayList.remove(4); // Remove values that have been calculated
                            arrayList.remove(3); // Remove values that have been calculated
                            arrayList.remove(2); // Remove values that have been calculated

                            arrayList.add(2, Integer.toString(total));
                            numLoops = arrayList.size(); // Re-calculate array size/number of loops
                        } else { // Evaluate the first calculation
                            if (arrayList.get(1).contains("+")) {
                                if (Integer.parseInt(arrayList.get(0)) + (Integer.parseInt(arrayList.get(2))) < 99999999) { // Make sure the calculation stays within the realms of an integer
                                    total = Integer.parseInt(arrayList.get(0)) + (Integer.parseInt(arrayList.get(2))); // Add the first and second digits
                                } else {
                                    textError.setText("Invalid - number too large");
                                    break;
                                }
                            } else if (arrayList.get(1).contains("-")) {
                                if(Integer.parseInt(arrayList.get(0)) - (Integer.parseInt(arrayList.get(2))) < 99999999) { // Make sure the calculation stays within the realms of an integer
                                    total = Integer.parseInt(arrayList.get(0)) - (Integer.parseInt(arrayList.get(2))); // Subtract the first and second digits
                                } else {
                                    textError.setText("Invalid - number too large");
                                    break;
                                }
                            } else if (arrayList.get(1).contains("*")) {
                                if (Integer.parseInt(arrayList.get(0)) * (Integer.parseInt(arrayList.get(2))) < 99999999) { // Make sure the calculation stays within the realms of an integer
                                    total = Integer.parseInt(arrayList.get(0)) * (Integer.parseInt(arrayList.get(2))); // Multiply the first and second digits
                                } else {
                                    textError.setText("Invalid - number too large");
                                    break;
                                }
                            } else if (arrayList.get(1).contains("/")) {
                                if (Integer.parseInt(arrayList.get(2)) == 0) { // Don't allow dividing by 0
                                    textError.setText("Invalid operation");
                                    break;
                                }
                                if (Integer.parseInt(arrayList.get(0))/Integer.parseInt(arrayList.get(2)) < 99999999) { // Make sure the calculation stays within the realms of an integer
                                    total = (Integer.parseInt(arrayList.get(0))) / (Integer.parseInt(arrayList.get(2))); // Divide the first and second digits
                                }
                                else { // Else do not complete the calculation
                                    textError.setText("Invalid - number too large");
                                    break;
                                }
                            }

                            arrayList.remove(2); // Remove values that have been calculated
                            arrayList.remove(1); // Remove values that have been calculated
                            arrayList.remove(0); // Remove values that have been calculated

                            arrayList.add(0, Integer.toString(total));
                            numLoops = arrayList.size(); // Re-calculate array size/number of loops
                        }
                    } else if (numLoops == 3) { // If size of arrayList is 3 then you have one calculation
                        if (arrayList.get(1).contains("+")) {
                            if (Integer.parseInt(arrayList.get(0)) + (Integer.parseInt(arrayList.get(2))) < 99999999) { // Make sure the calculation stays within the realms of an integer
                                total = Integer.parseInt(arrayList.get(0)) + (Integer.parseInt(arrayList.get(2))); // Add the first and second digits
                            } else {
                                textError.setText("Invalid - number too large");
                                break;
                            }
                        } else if (arrayList.get(1).contains("-")) {
                            if(Integer.parseInt(arrayList.get(0)) - (Integer.parseInt(arrayList.get(2))) < 99999999) { // Make sure the calculation stays within the realms of an integer
                                total = Integer.parseInt(arrayList.get(0)) - (Integer.parseInt(arrayList.get(2))); // Subtract the first and second digits
                            } else {
                                textError.setText("Invalid - number too large");
                                break;
                            }
                        } else if (arrayList.get(1).contains("*")) {
                            if (Integer.parseInt(arrayList.get(0)) * (Integer.parseInt(arrayList.get(2))) < 99999999) { // Make sure the calculation stays within the realms of an integer
                                total = Integer.parseInt(arrayList.get(0)) * (Integer.parseInt(arrayList.get(2))); // Multiply the first and second digits
                            } else {
                                textError.setText("Invalid - number too large");
                                break;
                            }
                        } else if (arrayList.get(1).contains("/")) {
                            if (Integer.parseInt(arrayList.get(2)) == 0) { // Don't allow dividing by 0
                                textError.setText("Invalid operation");
                                break;
                            }
                            if (Integer.parseInt(arrayList.get(0)) / (Integer.parseInt(arrayList.get(2))) < 99999999) { // Make sure the calculation stays within the realms of an integer
                                total = Integer.parseInt(arrayList.get(0)) / (Integer.parseInt(arrayList.get(2))); // Divide the first and second digits
                            }
                            else {
                                textError.setText("Invalid - number too large");
                                break;
                            }
                        }

                        arrayList.remove(2); // Remove values that have been calculated
                        arrayList.remove(1); // Remove values that have been calculated
                        arrayList.remove(0); // Remove values that have been calculated

                        arrayList.add(0, Integer.toString(total));
                        numLoops = arrayList.size(); // Re-calculate array size/number of loops
                    } else {
                        textError.setText("Invalid operation");
                        break;
                    }
                }
                System.out.println("total: " + total);

                textResult.setText(Integer.toString(total)); // Display the calculation
                textCalculation.setText(Integer.toString(total)); // Display the new value
                total = 0;
                string2 = "";
            }
        }
    }

    public void onClickCE (View v){
        TextView textResult = (TextView) findViewById(R.id.textResult); // Result field
        TextView textCalculation = (TextView) findViewById(R.id.textCalculation); // Calculation field
        TextView textError = (TextView) findViewById(R.id.textError); // Error field

        textResult.setText("0");
        textCalculation.setText("");
        textError.setText("");

        total = 0; // Reset the current calculation

        if (!arrayList.isEmpty()) {
            int i = arrayList.size();
            System.out.println(i);

            while (i > 0) { // Remove everything from arrayList
                arrayList.remove(i - 1); // Account for indexing from 0
                i--;
            }
        }
    }

    void singleCalculation(int total, int numLoops){
        TextView textError = (TextView) findViewById(R.id.textError); // Error field
        if (arrayList.get(1).contains("+")){ total = (Integer.parseInt(arrayList.get(0))) + ((Integer.parseInt(arrayList.get(2)))); // Add the first and second digits
        }
        else if (arrayList.get(1).contains("-")){ total = (Integer.parseInt(arrayList.get(0))) - (Integer.parseInt(arrayList.get(2))); // Subtract the first and second digits
        }
        else if (arrayList.get(1).contains("*")){ total = (Integer.parseInt(arrayList.get(0))) * ((Integer.parseInt(arrayList.get(2)))); // Multiply the first and second digits
        }
        else {
            if (arrayList.get(1).contains("/")) {
                if (Integer.parseInt(arrayList.get(2)) == 0) { // Don't allow dividing by 0
                    textError.setText("Invalid operation");
                    return;
                }
                total = (Integer.parseInt(arrayList.get(0))) / (Integer.parseInt(arrayList.get(2))); // Divide the first and second digits
            }
        }

        arrayList.remove(2); // Remove values that have been calculated
        arrayList.remove(1); // Remove values that have been calculated
        arrayList.remove(0); // Remove values that have been calculated

        arrayList.add(0, Integer.toString(total));
        numLoops = arrayList.size(); // Re-calculate array size/number of loops
    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
