package provided.util;

import java.util.concurrent.CountDownLatch;

/**
 * An abstract Runnable implementation that provides a service, block(), that will block 
 * a thread until the apply() is finished.   This class can be used to effectively convert 
 * an asynchronous (non-blocking) thread dispatch into a synchronous (blocking) one.
 * <br/>
 * Usage: Override the apply() method.   Use the instance of this class as you would 
 * normally use a Runnable to spawn a new thread.    Block the desired thread that is waiting 
 * for apply() to finish by calling the block() method of this object.
 * @author swong
 *
 */
public abstract class ABlockingRunnable implements Runnable {

	/**
	 * Latch used to block the thread calling block() until apply() is finished.
	 */
	private CountDownLatch  cl = new CountDownLatch(1);
	
	/**
	 * The thread that apply() and run() are running on.
	 */
	private Thread runThread;
	
	/**
	 * Overridden method of Runnable that is invoked by whatever is managing the threads.
	 */
	@Override
	public void run() {
		runThread = Thread.currentThread();
		apply();
		cl.countDown();
	}
	
	/**
	 * Blocks the current thread until apply() is finished.   An error will be printed 
	 * and the current thread will not be blocked if the thread being blocked is the same thread as 
	 * apply() runs on.  Note that this method will start blocking as soon as this object is instantiated, 
	 * not when apply() is actually started.
	 */
	public void block() {
		try {
			if (runThread!=null && runThread.equals(Thread.currentThread())){  // runThread will be null if run() hasn't yet been called.
				throw new UnsupportedOperationException("Cannot block the thread that apply() is run on.");
			}
			cl.await();
		} catch (Exception e) {
			System.err.println("ABlockingRunnable.block(): Exception = "+e);
			e.printStackTrace();
		}
	}
	
	
	/**
	 * The process to be run.   This cannot not be run on the same thread that calls block().
	 */
	abstract public void apply();
	
	

}
