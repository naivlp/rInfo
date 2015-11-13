package unlp.info.rInfo;

import unlp.info.rInfo.areas.*;
import unlp.info.rInfo.gui.Area;
import unlp.info.rInfo.gui.Workspace;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.TreeMap;

public class Programa implements Runnable {

	private ArrayList<Robot> robots;
	private ArrayList<Area> areas;
	private Thread thread;
	private boolean running;
	private TreeMap<Point, Boolean>esquinasBloqueadas;
	
	public static void main(String[] args) {

		Robot1 r1 = new Robot1("Robot1");
		r1.setPos(new Point(0, 0));
		Robot2 r2 = new Robot2("Robot2");
		r2.setPos(new Point(99, 99));
		Robot1 r3 = new Robot1("Robot3");
		r3.setPos(new Point(0, 19));
		r3.setColor(Color.green);

		AreaC area = new AreaC(0,0, 99,99);
		

		Programa program = new Programa();
		program.addRobot(r1);
		program.addRobot(r2);
		program.addRobot(r3);
		program.addArea(area);
		program.comenzar();
		
	}
	
	public Programa(){
		running = false;
		robots = new ArrayList<Robot>();
		areas = new ArrayList<Area>();
		esquinasBloqueadas = new TreeMap<>();
	}

	public void comenzar(){
		if(running){
			return;
		}
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run(){
		Workspace workspace = new Workspace(this);
		workspace.setVisible(true);
		
		for (Robot robot : robots) {
			robot.boot();
		}
	}

	public ArrayList<Robot> getRobots() {
		return robots;
	}

	public void addRobot(Robot robot){
		robots.add(robot);
	}

	public ArrayList<Area> getAreas() {
		return areas;
	}

	public void addArea(Area area){
		areas.add(area);
	}

	public boolean isEsquinaBlocked(Point esquina){
		return esquinasBloqueadas.get(esquina);
	}

	public synchronized void bloquearEsquina(Point esquina){
		esquinasBloqueadas.put(esquina, true);
	}

	public synchronized void liberarEsquina(Point esquina){
		esquinasBloqueadas.put(esquina, false);
		notifyAll();
	}
}