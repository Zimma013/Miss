package simulation;

import GUI.*;
import inOut.*;
import objects.ThePoI;
import objects.TheUnits;

public class Simulation {
    private int size = 3; // wielkosc "piksela"+1
    private int width = 300; // szerokosc okna
    private int height = 200; // wysokosc okna
    private int exist = 5; // % jednostek na mapie
    private int sick = 5; // % chorych z zyjacych jednostek
    private int infected = 2; // % infekujacy jeszcze nie chorych
    private int immune = 5; // % odpornych na chorobe
    private int toInfect = 2; // szansa na zainfekowanie w %

    private int[] initialParameters = {exist, sick, infected, immune, toInfect};
    private boolean simulate = false;

    private String imgPath = "img/testImg3.png"; //sciezka obrazka
    private String logPath = "log/sim.log"; //sciezka pliku do zapisu przebiegu symulacji

    // logger
    private Log logger = new Log(logPath);

    // obsługa obrazu
    private ImageReader image = new ImageReader(imgPath);

    // obiekt zawierający świat oraz generujący losowo mieszkańców
    // objects.TheUnits theUnits = new objects.TheUnits(width, height, exist, sick, infected,
    // immune, toInfect, thePoI);
    ThePoI thePoi = image.getPoIsFromImage();
    private TheUnits theUnits = new TheUnits(image.getWidth(), image.getHeight(), initialParameters, thePoi);

    // obiekty do rysowania
    private Drawer drawer = new Drawer(theUnits, width, height, size, thePoi);
    private MenuPanel menu = new MenuPanel(width, height, this, initialParameters);
    private MyFrame myFrame = new MyFrame(drawer);
    private MyFrame myFrame2 = new MyFrame(menu);
    // głupi separator dla wejść loga
    private String separator = ",";

    public void startNewSimulation() {
        this.simulate = false;
        this.theUnits = new TheUnits(image.getWidth(), image.getHeight(), initialParameters, image.getPoIsFromImage());
        this.drawer = new Drawer(theUnits, width, height, size, image.getPoIsFromImage());
        this.logger = new Log(logPath);
        myFrame.updateFrame(drawer);
    }

    public void resumeSimulation() {
        this.simulate = true;
    }

    public void stopSimulation() {
        this.simulate = false;
    }

    public void setParameters(int[] parameters) {
        this.initialParameters = parameters;
    }

    // teraz skonczona petla symulacji
    public void simualtion() {
        // while (unitsC > immuneC) {
        while (true) {
            System.out.print("");
            if (simulate) {
                System.out.println("Day: " + theUnits.getDayCounter());
                System.out.println("Time: " + theUnits.getTimeInDayCounter());
                System.out.println("Units: " + theUnits.countExisting());
                System.out.println("Healthy: " + theUnits.countHealthy());
                System.out.println("Infected/NotSick: " + theUnits.countCarriers());
                System.out.println("Infected: " + theUnits.countInfected());
                System.out.println("Immune: " + theUnits.countImmune());
                System.out.println();

                logger.getLogLine("" + theUnits.getDayCounter() + separator + theUnits.getTimeInDayCounter() + separator
                        + theUnits.countHealthy() + separator + theUnits.countCarriers() + separator
                        + (theUnits.countInfected() - theUnits.countCarriers()) + separator + theUnits.countImmune());

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                theUnits.makeMove();
                drawer.repaint();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                theUnits.infect();
                drawer.repaint();
                logger.makeLogFile();
            } else {
            }
        }
    }
}