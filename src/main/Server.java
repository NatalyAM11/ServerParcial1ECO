package main;

import processing.core.PApplet;

public class Server extends PApplet implements OnMessageListener {
	
	TCPSingleton tcp;
	int pantalla;
	
	//Jugador
	Jugador jugador;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("main.Server");
	}
	
	
	public void settings() {
		
		size(800,500);
		
	}
	
	
	
	public void setup() {
		
		tcp= TCPSingleton.getInstance();
		tcp.setObserver(this);
		
		pantalla=0;
		
	}
	
	
	
	
	public void draw() {
		background (0);
		
		noStroke();
		
		//Valido que cuando el objeto ya no sea nulo, me deje seguir a la otra pantalla y en esa pinto el avatar
		if(tcp.objeto!=null) {
			pantalla=1;
		}
		
		//Hago un switch para controlar las "Pantallas", la primera donde no aparece nada por que el usuario
		//no ha puesto ningun dato, y la otra donde ya pinta al avatar
		
		switch(pantalla) {
		
		//Pantalla inicial
		case 0:
			fill(255);
			textSize(20);
			text("Para ver a tu avatar primero debes nombrarlo",180,250);
			
			break;
		
		//Pantalla con el avatar
		case 1:
			
			if(tcp.objeto!=null) {
				
				//Pinto la bolita
				fill(jugador.getR(),jugador.getG(),jugador.getB());
				ellipse(jugador.getX(),jugador.getY(),60,60);
				
				
				//pinto el nombre
				fill(255);
				text(jugador.getNombre(),jugador.getX()-25,jugador.getY()-40);
				
			}
			
			break;
		}
	
	}

	
	
	
	//Metodo observer
	public void onMessage(Jugador jugador) {
		
		this.jugador=jugador;
		
	}

}
