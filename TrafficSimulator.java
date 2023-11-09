import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
//import static org.junit.Assert.assertEquals;
//import org.junit.Test;
import java.util.PriorityQueue;
import java.util.*;
import javax.swing.Timer;


public class TrafficSimulator extends JFrame {
    private final int roadWidth = 150;
    private final int roadLength = 400;
    private final int intersectionSize = 150;
    private final int carWidth = 30;
    private final int carHeight = 30;

    public final int carWidth2 = 30;
    public final int carHeight2 = 30;
    private final int semaphoreINTERSECTION = 1;

    private Road[] roads = new Road[4];
    private JPanel intersection;
    private CarAnimation carAnimation;
    private Car car1;
    private Car2 car2;
    private Car3 car3;
    private Car4 car4;
    private int intersectioncars = 0;


//    private static boolean Semaphore=true;



    public TrafficSimulator() {
//        intersectionSemaphore = new Semaphore(1);
        BooleanSemaphore intersectionSemaphore = new BooleanSemaphore();
//        Intersection pintersection = new Intersection();


        setTitle("Traffic Simulator");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Create the intersection as a JPanel
        intersection = new JPanel() {
            @Override
            protected void paintComponent(Graphics c) {
                super.paintComponent(c);
                c.setColor(Color.WHITE);
                c.fillRect(0, 0, intersectionSize, intersectionSize);
            }
        };
        intersection.setBounds(300, 300, intersectionSize, intersectionSize);
        add(intersection);

        // Create and position the roads
        roads[0] = new Road(300, 0, roadWidth, roadLength, "North", intersection, 2);
        roads[1] = new Road(300, 400, roadWidth, roadLength, "South", intersection, 2);
        roads[2] = new Road(400, 300, roadLength, roadWidth, "East", intersection, 2, true);
        roads[3] = new Road(0, 300, roadLength, roadWidth, "West", intersection, 2, true);

        for (Road road : roads) {
            add(road);
        }

        // Create CarAnimation panel
        carAnimation = new CarAnimation();
        carAnimation.setBounds(0, 0, 800, 800);
        add(carAnimation);

        // Ensure the CarAnimation panel is on top
        setComponentZOrder(carAnimation, 0);
        Random random = new Random();
        Timer carGenerator = new Timer(9000, e -> {
            int randomValue1 = random.nextInt(4);; // Generate a random value for car 1 direction
            int randomValue2 = random.nextInt(4);// Generate a random value for car 2 direction
            int randomValue3 = random.nextInt(4); // Generate a random value for car 3 direction
            int randomValue4 = random.nextInt(4); // Generate a random value for car 4 direction


            if (randomValue1 == 0 || randomValue1 == 3) {
                carAnimation.addCar(intersectionSemaphore,1);
            }
            if (randomValue2 == 1 || randomValue2 == 0) {
                carAnimation.addCar2(intersectionSemaphore,2);
            }
            if (randomValue3 == 2 || randomValue3 == 3) {
                carAnimation.addCar3(intersectionSemaphore,3);
            }
            if (randomValue4 == 3 || randomValue4 == 1) {
                carAnimation.addCar4(intersectionSemaphore,4);
            }
        });
        carGenerator.start();


//            carAnimation.addCar(intersectionSemaphore,1);
//            carAnimation.addCar2(intersectionSemaphore,3);
//            carAnimation.addCar3(intersectionSemaphore,4);
//            carAnimation.addCar4(intersectionSemaphore,2);
//        random.nextInt(4)


//        Random random = new Random();
//        Timer carGenerator = new Timer(8310, e -> {
//            int randomValue = random.nextInt(6);
//            if(randomValue == 3 ) {
//                carAnimation.addCar(intersectionSemaphore, 3);
//                carAnimation.addCar2(intersectionSemaphore, 4);
//            }
//            if(randomValue == 1) {
//                carAnimation.addCar3(intersectionSemaphore, 3);
//                carAnimation.addCar4(intersectionSemaphore, 4);
//            }
//            if(randomValue == 2)
//            {
//                carAnimation.addCar2(intersectionSemaphore, 3);
//                carAnimation.addCar4(intersectionSemaphore, 4);
//            }
//            if(randomValue == 0){
//                carAnimation.addCar3(intersectionSemaphore, 3);
//                carAnimation.addCar(intersectionSemaphore,4 );
//            }
//            if(randomValue == 4){
//                carAnimation.addCar4(intersectionSemaphore, 3);
//                carAnimation.addCar(intersectionSemaphore,4);
//            }
//            if(randomValue == 5){
//                carAnimation.addCar3(intersectionSemaphore, 3);
//                carAnimation.addCar2(intersectionSemaphore, 4);
//            }
//
//        });
//        carGenerator.start();

//        Timer carGenerator = new Timer(15000, e -> {
//
//            carAnimation.addCar(intersectionSemaphore,1);
//
//            carAnimation.addCar2(intersectionSemaphore,2);
//
//            carAnimation.addCar3(intersectionSemaphore,3);
//
//            carAnimation.addCar4(intersectionSemaphore,4);
//
//        });
//        carGenerator.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TrafficSimulator simulator = new TrafficSimulator();
            simulator.setVisible(true);
        });
    }

    class Road extends JPanel {
        private String direction;
        private JPanel intersection;
        private int numLanes;
        private boolean horizontallyDivided;

        public Road(int x, int y, int width, int length, String direction, JPanel intersection, int numLanes, boolean horizontallyDivided) {
            setBounds(x, y, width, length);
            this.direction = direction;
            this.intersection = intersection;
            this.numLanes = numLanes;
            this.horizontallyDivided = horizontallyDivided;
        }

        public Road(int x, int y, int width, int length, String direction, JPanel intersection, int numLanes) {
            this(x, y, width, length, direction, intersection, numLanes, false);
        }

        public boolean isHorizontallyDivided() {
            return horizontallyDivided;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Set the background color of the road to gray
            g.setColor(Color.GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());

            // Draw white lines to divide the road
            g.setColor(Color.WHITE);
            if (horizontallyDivided) {
                int laneHeight = getHeight() / numLanes;
                for (int i = 1; i < numLanes; i++) {
                    int laneY = i * laneHeight;
                    g.fillRect(0, laneY, getWidth(), 2);
                }
            } else {
                int laneWidth = getWidth() / numLanes;
                for (int i = 1; i < numLanes; i++) {
                    int laneX = i * laneWidth;
                    g.fillRect(laneX, 0, 2, getHeight());
                }
            }
        }
    }

    class CarAnimation extends JPanel {
        private final int frameWidth = 800;
        private final int frameHeight = 800;
        public final int carWidth = 30;
        public final int carHeight = 30;

        private ArrayList<Car> cars = new ArrayList<>();
        private ArrayList<Car2> cars2 = new ArrayList<>();
        private ArrayList<Car3> cars3 = new ArrayList(); // Fixed the declaration
        private ArrayList<Car4> cars4 = new ArrayList(); // Fixed the declaration
//        private Intersection priorityIntersection = new Intersection();
        public CarAnimation() {
            setLayout(null);
        }
//        intersection.addCar(car1);


        public void addCar(BooleanSemaphore intersectionSemaphore,int priority) {
            Car car = new Car(intersectionSemaphore,priority);
            Thread carThread = new Thread(new Car(intersectionSemaphore,priority));
            cars.add(car);
//            priorityIntersection.addCar(car);
            car.start();
            carThread.start();

        }

        public void addCar2(BooleanSemaphore intersectionSemaphore,int priority) {
            Thread carThread2 = new Thread(new Car2(intersectionSemaphore,priority));
            Car2 car2 = new Car2(intersectionSemaphore,priority);
            cars2.add(car2);
//            priorityIntersection.addCar(car2);
            car2.start();
            carThread2.start();
        }

        public void addCar3(BooleanSemaphore intersectionSemaphore,int priority) {
            Car3 car3 = new Car3(intersectionSemaphore,priority);
            Thread carThread3 = new Thread(new Car3(intersectionSemaphore,priority));
            cars3.add(car3);
//            priorityIntersection.addCar(car3);
            car3.start();
            carThread3.start();

        }

        public void addCar4(BooleanSemaphore intersectionSemaphore,int priority) {
            Car4 car4 = new Car4(intersectionSemaphore,priority);
            Thread carThread4 = new Thread(new Car4(intersectionSemaphore,priority));
            cars4.add(car4);
//            priorityIntersection.addCar(car4);
            car4.start();
            carThread4.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Paint the roads and intersection
            for (Road road : roads) {
                g.setColor(Color.GRAY);
                g.fillRect(road.getX(), road.getY(), road.getWidth(), road.getHeight());
                g.setColor(Color.WHITE);

                if (road.isHorizontallyDivided()) {
                    int laneHeight = road.getHeight() / road.numLanes;
                    for (int i = 1; i < road.numLanes; i++) {
                        int laneY = road.getY() + i * laneHeight;
                        g.fillRect(road.getX(), laneY, road.getWidth(), 2);
                    }
                } else {
                    int laneWidth = road.getWidth() / road.numLanes;
                    for (int i = 1; i < road.numLanes; i++) {
                        int laneX = road.getX() + i * laneWidth;
                        g.fillRect(laneX, road.getY(), 2, road.getHeight());
                    }
                }
            }

            g.setColor(Color.WHITE);
            g.fillRect(intersection.getX(), intersection.getY(), intersectionSize, intersectionSize);

            // Paint cars
            for (Car car : cars) {
                g.setColor(Color.RED);
                g.fillRect(car.x, car.y, carWidth, carHeight);
            }
            for (Car2 car2 : cars2) {
                g.setColor(Color.RED);
                g.fillRect(car2.x, car2.y, carWidth, carHeight);
            }
            for (Car3 car3 : cars3) {
                g.setColor(Color.RED);
                g.fillRect(car3.x, car3.y, carWidth2, carHeight2);
            }
            for (Car4 car4 : cars4) {
                g.setColor(Color.RED);
                g.fillRect(car4.x, car4.y, carWidth2, carHeight2);
            }
        }
    }

    public class BooleanSemaphore {
        private volatile boolean value = true;

        public synchronized void up() {
            value = false;
            notify();
        }

        public synchronized void down() throws InterruptedException {
            value = true;
        }

        public boolean getValue() {
            return value;
        }
    }

    class Car extends Thread implements Runnable{
        private int x, y;
        private int isReader;
        private final int frameWidth = 800;
        private final int frameHeight = 800;
        private final int intersectionX = 328;
        private final int intersectionY = 320;
        private final int intersectionXR = 392;
        private final int intersectionYU = 400;
        private final BooleanSemaphore intersectionSemaphore;
        private int priority;


        Car(BooleanSemaphore semaphore,int priority) {
            x = -50;
            y = 320;
            this.intersectionSemaphore = semaphore;
            this.priority = priority;
        }
        public int getCarPriority() {
            return priority;
        }

        @Override

        public void run() {
            isReader = new Random().nextInt(4);
            try {
                while (y > -50 || y < 850 || x < 850 || x > -50) {
                    // Handle intersection and priority logic
                    handleIntersection();

                    // Rest of the car movement logic
                    moveCar(isReader);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void handleIntersection() throws InterruptedException {
            if (y == 320 && x == 270) {
                System.out.println("semaphore :" + intersectionSemaphore.getValue());
                if (intersectionSemaphore.getValue() == false) {
                    System.out.println("semaphore :" + intersectionSemaphore.getValue());
                    intersectioncars++;
                    if(intersectioncars>=3) {
                        if (priority == 1) {
                            Thread.sleep(3000);
                        }
                        if (priority == 2) {
                            Thread.sleep(4500);
                        }
                        if (priority == 3) {
                            Thread.sleep(6000);
                        }
                        if (priority == 4) {
                            Thread.sleep(7500);
                        }
                    }
                    else{
                        Thread.sleep(2000);
                    }
                    System.out.println("Count " + intersectioncars + " waited at the intersection");
                    intersectioncars--;
                }  if (intersectionSemaphore.getValue() == true) {
                    // Car reached the intersection, up the semaphore
                    intersectionSemaphore.up();
//                    intersectioncars++;
                    System.out.println("Car " + priority + " passed the intersection");
//                    System.out.println("semaphore :" + intersectionSemaphore.getValue());
                }
            }

            if ((x == 450 && y == 320) || (x == 328 && y == 300) || (x == 392 && y == 450) || (x == 328 && y == 400)) {
                // Car is near the intersection, down the semaphore
                intersectionSemaphore.down();
//                System.out.println("semaphore :" + intersectionSemaphore.getValue());

                System.out.println("Car " + priority + " left the intersection");
            }
        }



        private void moveCar(int turn) {
            if (x == intersectionX && turn == 0) {
                makeLeftTurn();
            }
            if ((x == intersectionX && turn == 1) || (y==intersectionYU && turn==1)) {
                if (y == intersectionYU) {
                    reverse();
                }
                if (y < intersectionYU) {
                    makeRightTurn();
                }

            }
            if (x == intersectionXR && turn == 2) {
                makeRightTurn();
            }
            if (x <= intersectionXR && y == intersectionY || turn == 3) {
                x += 2;
                SwingUtilities.invokeLater(() -> {
                    repaint();
                });
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }



        private void makeLeftTurn() {

            y -= 2;

            SwingUtilities.invokeLater(() -> {
                repaint();
            });

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        private void makeRightTurn() {

            y += 2;
//

            SwingUtilities.invokeLater(() -> {
                repaint();
            });

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
        private void reverse() {
            x -= 2;
            SwingUtilities.invokeLater(() -> {
                repaint();
            });

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    class Car2 extends Thread implements Runnable{
        private int x, y;
        private int isReader;
        private final int frameWidth = 800;
        private final int frameHeight = 800;
        private final int intersectionX = 396;
        private final int intersectionY = 400;
        private final int intersectionXR = 328;
        private final int intersectionYU = 320;
        private int priority;
        private final BooleanSemaphore intersectionSemaphore;

        Car2(BooleanSemaphore semaphore, int priority) {
            x = frameWidth+200;
            y = 400;
            this.intersectionSemaphore=semaphore;
            this.priority = priority;
        }
        public int getCarPriority() {
            return priority;
        }

        @Override
        public void run() {
            isReader = new Random().nextInt(4);

            while (y > -50 || y < 850 || x < 850 || x > -50) {
                // Handle intersection and priority logic
                handleIntersection();

                // Rest of the car movement logic
                moveCar(isReader);
            }
        }

        private void handleIntersection() {
            try {
                if (y == 400 && x == 450) {
                    if (intersectionSemaphore.getValue() == false) {
                        // Semaphore is already up, sleep for 3000 milliseconds
                        intersectioncars++;
                        System.out.println("semaphore :" + intersectionSemaphore.getValue());
                        if(intersectioncars>=3) {
                            if (priority == 1) {
                                Thread.sleep(3000);
                            }
                            if (priority == 2) {
                                Thread.sleep(4500);
                            }
                            if (priority == 3) {
                                Thread.sleep(6000);
                            }
                            if (priority == 4) {
                                Thread.sleep(7500);
                            }
                        }
                        else{
                            Thread.sleep(2000);
                        }
                        System.out.println("Count " + intersectioncars + " waited at the intersection");
                        intersectioncars--;
                    }  if (intersectionSemaphore.getValue() == true) {
                        // Car reached the intersection, up the semaphore
                        intersectionSemaphore.up();
//                        intersectioncars++;
                        System.out.println("Car " + priority + " passed the intersection");
//                        System.out.println("semaphore :" + intersectionSemaphore.getValue());
                    }
                }

                if ((x == 300 && y == 400) || (x == 392 && y == 450) || (x == 328 && y == 300) || (x == 392 && y == 320)) {
                    // Car is near the intersection, down the semaphore
                    intersectionSemaphore.down();
//                    System.out.println("semaphore :" + intersectionSemaphore.getValue());
                    System.out.println("Car " + priority + " left the intersection");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void moveCar(int turn) {
            if (x == intersectionX && turn==0) {
                makeLeftTurn();
            }
            if ((x == intersectionX && turn==1) || (y==intersectionYU && turn==1)) {
                if (y == intersectionYU) {
                    reverse();
                }
                if (y > intersectionYU ) {
                    makeRightTurn();
                }


            }
            if (x == intersectionXR && turn==2) {
                makeRightTurn();
            }
            if (x >= intersectionXR && y == intersectionY || turn == 3){
                x -= 2;

                SwingUtilities.invokeLater(() -> {
                    repaint();
                });

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        private void makeLeftTurn() {

            y += 2;

            SwingUtilities.invokeLater(() -> {
                repaint();
            });

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        private void makeRightTurn() {

            y -= 2;
//

            SwingUtilities.invokeLater(() -> {
                repaint();
            });

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
        private void reverse() {
            x += 2;
            SwingUtilities.invokeLater(() -> {
                repaint();
            });

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    class Car3 extends Thread implements Runnable{
        private int x, y;
        private int isReader;
        private final int frameWidth = 800;
        private final int frameHeight = 800;
        private final int intersectionX = 330;
        private final int intersectionY = 400;
        private final int intersectionYR = 320;
        private final int intersectionXU = 398;
        private final BooleanSemaphore intersectionSemaphore;
        private int priority;
        Car3(BooleanSemaphore semaphore, int priority) {
            x = 330;
            y = frameHeight+300;
            this.intersectionSemaphore=semaphore;
            this.priority = priority;
        }
        public int getCarPriority() {
            return priority;
        }

        @Override
        public void run() {
            isReader = new Random().nextInt(4);
            while (y > -50 || y < 850 || x < 850 || x > -50) {
                // Handle intersection and priority logic
                handleIntersection();

                // Rest of the car movement logic
                moveCar(isReader);
            }
        }

        private void handleIntersection() {
            try {
                if (y == 450 && x == 330) {
                    if (intersectionSemaphore.getValue() == false) {
                        // Semaphore is already up, sleep for 3000 milliseconds
                        intersectioncars++;
                        if(intersectioncars>=3) {
                            if (priority == 1) {
                                Thread.sleep(3000);
                            }
                            if (priority == 2) {
                                Thread.sleep(4500);
                            }
                            if (priority == 3) {
                                Thread.sleep(6000);
                            }
                            if (priority == 4) {
                                Thread.sleep(7500);
                            }
                        }
                        else{
                            Thread.sleep(2000);
                        }

                        System.out.println("Count " + intersectioncars + " waited at the intersection");
                        intersectioncars--;
                    }if(intersectionSemaphore.getValue() == true){
                        // Car reached the intersection, up the semaphore
                        intersectionSemaphore.up();
//                        intersectioncars++;
                        System.out.println("Car " + priority + " passed the intersection");
                    }
                }

                if ((x == 330 && y == 300) || (x == 300 && y == 400) || (x == 450 && y == 320) || (x == 398 && y == 400)) {
                    // Car is near the intersection, down the semaphore
                    intersectionSemaphore.down();

                    System.out.println("Car " + priority + " left the intersection");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        private void moveCar(int turn) {
            if (y == intersectionY && turn==0) {
                makeLeftTurn();
            }
            if ((y == intersectionY && turn==1) || (x==intersectionXU&& turn==1)) {
                if (x == intersectionXU) {
                    reverse();
                }
                if (x < intersectionXU ) {
                    makeRightTurn();
                }
            }
            if (y == intersectionYR && turn==2) {
                makeRightTurn();
            }
            if (y >= intersectionYR && x == intersectionX || turn == 3){
                y -= 2;

                SwingUtilities.invokeLater(() -> {
                    repaint();
                });

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
        private void makeLeftTurn() {

            x -= 2;

            SwingUtilities.invokeLater(() -> {
                repaint();
            });

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        private void makeRightTurn() {

            x += 2;
//

            SwingUtilities.invokeLater(() -> {
                repaint();
            });

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        private void reverse() {
            y += 2;
            SwingUtilities.invokeLater(() -> {
                repaint();
            });

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class Car4 extends Thread implements Runnable{
        private int x, y;
        private int isReader;
        private final int frameWidth = 800;
        private final int frameHeight = 800;
        private final int intersectionX =395;
        private final int intersectionY = 320;
        private final int intersectionYR = 400;
        private final int intersectionXU = 330;
        private final BooleanSemaphore intersectionSemaphore;
        private int priority;

        Car4(BooleanSemaphore semaphore, int priority) {
            x = 395;
            y = -300;
            this.intersectionSemaphore=semaphore;
            this.priority = priority;
        }
        public int getCarPriority() {
            return priority;
        }

        @Override
        public void run() {
            isReader = new Random().nextInt(3);
            while (y > -50 || y < 850 || x < 850 || x > -50) {
                // Handle intersection and priority logic
                handleIntersection();

                // Rest of the car movement logic
                moveCar(isReader);
            }
        }

        private void handleIntersection() {
            try {
                if (y == 270 && x == 395 ) {
                    if (intersectionSemaphore.getValue() == false) {
                        // Semaphore is already up, sleep for 3000 milliseconds
                        intersectioncars++;
                        if(intersectioncars>=3) {
                            if (priority == 1) {
                                Thread.sleep(3000);
                            }
                            if (priority == 2) {
                                Thread.sleep(4500);
                            }
                            if (priority == 3) {
                                Thread.sleep(6000);
                            }
                            if (priority == 4) {
                                Thread.sleep(7500);
                            }
                        }
                        else{
                            Thread.sleep(2000);
                        }
                        System.out.println("Count " + intersectioncars + " waited at the intersection");
                        intersectioncars--;
                    }
                    if(intersectionSemaphore.getValue() == true){
                        // Car reached the intersection, up the semaphore
                        intersectionSemaphore.up();
//                        intersectioncars++;
                        System.out.println("Car " + priority + " passed the intersection");
                    }
                }

                if ((x == 395 && y == 450) || (x == 450 && y == 320) || (x == 300 && y == 400) || (x == 330 && y == 300)) {
                    // Car is near the intersection, down the semaphore
                    intersectionSemaphore.down();

                    System.out.println("Car " + priority + " left the intersection");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        private void moveCar(int turn) {
            if (y == intersectionY && turn==4) {
                makeLeftTurn();
            }
            if ((y == intersectionY && turn==0) || (x<=intersectionXU && turn==0)) {
                if (x <= intersectionXU) {
                    reverse();
                }
                if (x > intersectionXU ) {
                    makeRightTurn();
                }
            }
            if (y == intersectionYR && turn==1) {
                makeRightTurn();
            }
            if (y <= intersectionYR && x == intersectionX || turn == 2){
                y += 2;

                SwingUtilities.invokeLater(() -> {
                    repaint();
                });

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
        private void makeLeftTurn() {

            x += 2;

            SwingUtilities.invokeLater(() -> {
                repaint();
            });

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        private void makeRightTurn() {

            x -= 2;
//

            SwingUtilities.invokeLater(() -> {
                repaint();
            });

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        private void reverse() {
            y -= 2;
            SwingUtilities.invokeLater(() -> {
                repaint();
            });

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
