package it.unibo.bls18.obj;
import it.unibo.bls18.interfaces.IResourceLocalObserver;
 

public abstract class ResourceLocalObserver implements IResourceLocalObserver{
 
 	protected ResourceLocalObserver( ) {}

 	protected void showMsg(String msg) {
 		System.out.println( msg ) ;
 	}
 
}
