/*
 * This file is part of Jalopy.
 *
 * Copyright (c) 2001-2004, Marco Hunsicker. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of the Jalopy Group nor the names of its contributors may be
 * used to endorse or promote products derived from this software without specific
 * prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package de.hunsicker.swing.util;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import de.hunsicker.util.concurrent.Callable;
import de.hunsicker.util.concurrent.FutureResult;
import de.hunsicker.util.concurrent.ThreadFactory;
import de.hunsicker.util.concurrent.ThreadFactoryUser;
import de.hunsicker.util.concurrent.TimedCallable;
import de.hunsicker.util.concurrent.TimeoutException;


//J- needed only as a workaround for a Javadoc bug
import java.lang.Long;
//J+

/**
 * An abstract class that you subclass to perform GUI-related work in a dedicated thread.
 * 
 * <p>
 * This class was adapted from the SwingWorker written by Hans Muller and presented in
 * "Using a Swing Worker Thread" in the Swing Connection - <a
 * href="http://java.sun.com/products/jfc/tsc/articles/threads/threads2.html">http://java.sun.com/products/jfc/tsc/articles/threads/threads2.html</a>
 * </p>
 * 
 * <p>
 * A closely related version of this class is described in "The Last Word in Swing
 * Threads" in the Swing Connection - <a
 * href="http://java.sun.com/products/jfc/tsc/articles/threads/threads3.html">http://java.sun.com/products/jfc/tsc/articles/threads/threads3.html</a>
 * </p>
 * 
 * <p>
 * This SwingWorker is a ThreadFactoryUser and implements Runnable. The default thread
 * factory creates low-priority worker threads. A special constructor is provided for
 * enabling a timeout. When the timeout expires, the worker thread is interrupted.
 * </p>
 * 
 * <p>
 * Note: Using a timeout of {@link Long#MAX_VALUE} will not impose a timeout but will
 * create an additional thread of control that will respond to an interrupt even if the
 * {@link #construct} implementation ignores them.
 * </p>
 * 
 * <p>
 * <b>Sample Usage</b>
 * </p>
 * 
 * <p>
 * <pre class="snippet">
 * import EDU.oswego.cs.dl.util.concurrent.TimeoutException;
 * import EDU.oswego.cs.dl.util.concurrent.misc.SwingWorker;
 * 
 * public class SwingWorkerDemo
 *     extends javax.swing.JApplet
 * {
 *     private static final int TIMEOUT = 5000; // 5 seconds
 *     private javax.swing.JButton start;
 *     private javax.swing.JLabel status;
 *     private SwingWorker worker;
 * 
 *     public SwingWorkerDemo()
 *     {
 *         status = new javax.swing.JLabel("Ready");
 *         status.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
 *         getContentPane().add(status, java.awt.BorderLayout.CENTER);
 * 
 *         start = new javax.swing.JButton("Start");
 *         getContentPane().add(start, java.awt.BorderLayout.SOUTH);
 *         start.addActionListener(new java.awt.event.ActionListener() {
 *             public void actionPerformed(java.awt.event.ActionEvent evt)
 *             {
 *                 if (start.getText().equals("Start"))
 *                 {
 *                     start.setText("Stop");
 *                     status.setText("Working...");
 *                     worker = new DemoSwingWorker(TIMEOUT);
 *                     worker.start();
 *                 }
 *                 else
 *                 {
 *                     worker.interrupt();
 *                 }
 *             }
 *         });
 *     }
 * 
 *     private class DemoSwingWorker
 *         extends SwingWorker
 *     {
 *         private static final java.util.Random RAND = new java.util.Random();
 * 
 *         public DemoSwingWorker(long msecs)
 *         {
 *             super(msecs);
 *         }
 * 
 *         protected Object construct()
 *             throws InterruptedException
 *         {
 *             // Take a random nap. If we oversleep, the worker times out.
 *             Thread.sleep(RAND.nextInt(2 * TIMEOUT));
 * 
 *             return "Success";
 *         }
 * 
 *         protected void finished()
 *         {
 *             start.setText("Start");
 * 
 *             try
 *             {
 *                 Object result = get();
 *                 status.setText((String)result);
 *             }
 *             catch (java.lang.reflect.InvocationTargetException e)
 *             {
 *                 Throwable ex = e.getTargetException();
 * 
 *                 if (ex instanceof TimeoutException)
 *                 {
 *                     status.setText("Timed out.");
 *                 }
 *                 else if (ex instanceof InterruptedException)
 *                 {
 *                     status.setText("Interrupted.");
 *                 }
 *                 else
 *                 {
 *                     status.setText("Exception: " + ex);
 *                 }
 *             }
 *             catch (InterruptedException ex)
 *             {
 *                 // event-dispatch thread won't be interrupted
 *                 throw new IllegalStateException(ex + "");
 *             }
 *         }
 *     }
 * }
 * </pre>
 * </p>
 *
 * @author Joseph Bowbeer
 * @author Hans Muller
 * @version 3.0
 */
public abstract class SwingWorker
    extends ThreadFactoryUser
    implements Runnable
{
    //~ Static variables/initializers ----------------------------------------------------

    /** Default thread factory. Creates low priority worker threads. */
    private static final ThreadFactory FACTORY =
        new ThreadFactory()
        {
            public Thread newThread(Runnable command)
            {
                Thread t = new Thread(command);
                t.setPriority(Thread.MIN_PRIORITY + 1);

                return t;
            }
        };


    //~ Instance variables ---------------------------------------------------------------

    /** Holds the value to be returned by the <code>get</code> method. */
    private final FutureResult result = new FutureResult();

    /** Worker thread. */
    private Thread thread;

    /** Maximum time to wait for worker to complete. */
    private final long timeout;

    //~ Constructors ---------------------------------------------------------------------

    /**
     * Creates new SwingWorker with no timeout.
     */
    public SwingWorker()
    {
        this(FACTORY, 0);
    }


    /**
     * Creates new SwingWorker with specified timeout.
     *
     * @param msecs timeout in milliseconds, or <code>0</code> for no time limit.
     */
    public SwingWorker(long msecs)
    {
        this(FACTORY, msecs);
    }


    /**
     * Creates new SwingWorker with specified thread factory and timeout.
     *
     * @param factory factory for worker threads.
     * @param msecs timeout in milliseconds, or <code>0</code> for no time limit.
     *
     * @throws IllegalArgumentException DOCUMENT ME!
     */
    protected SwingWorker(
        ThreadFactory factory,
        long          msecs)
    {
        setThreadFactory(factory);

        if (msecs < 0)
        {
            throw new IllegalArgumentException("timeout=" + msecs);
        }

        timeout = msecs;
    }

    //~ Methods --------------------------------------------------------------------------

    /**
     * Get the exception, or <code>null</code> if there isn't one (yet). This does not
     * wait until the worker is ready, so should ordinarily only be called if you know
     * it is.
     *
     * @return the exception encountered by the {@link #construct} method wrapped in an
     *         InvocationTargetException.
     */
    public InvocationTargetException getException()
    {
        return result.getException();
    }


    /**
     * Return whether the {@link #get} method is ready to return a value.
     *
     * @return true if a value or exception has been set.
     */
    public boolean isReady()
    {
        return result.isReady();
    }


    /**
     * Returns timeout period in milliseconds. Timeout is the maximum time to wait for
     * worker to complete. There is no time limit if timeout is <code>0</code>
     * (default).
     *
     * @return DOCUMENT ME!
     */
    public long getTimeout()
    {
        return timeout;
    }


    /**
     * Return the value created by the {@link #construct} method, waiting if necessary
     * until it is ready.
     *
     * @return the value created by the {@link #construct} method
     *
     * @exception InterruptedException if current thread was interrupted
     * @exception InvocationTargetException if the constructing thread encountered an
     *            exception or was interrupted.
     */
    public Object get()
      throws InterruptedException, InvocationTargetException
    {
        return result.get();
    }


    /**
     * Stops the worker and sets the exception to InterruptedException.
     */
    public synchronized void interrupt()
    {
        if (thread != null)
        {
            /* Try-catch is workaround for JDK1.2 applet security bug.
               On some platforms, a security exception is thrown if an
               applet interrupts a thread that is no longer alive. */
            try
            {
                thread.interrupt();
            }
            catch (Throwable ex)
            {
                ;
            }
        }

        result.setException(new InterruptedException());
    }


    /**
     * Calls the {@link #construct} method to compute the result, and then invokes the
     * {@link #finished} method on the event dispatch thread.
     */
    public void run()
    {
        Callable function =
            new Callable()
            {
                public Object call()
                  throws Exception
                {
                    return construct();
                }
            };

        Runnable doFinished =
            new Runnable()
            {
                public void run()
                {
                    finished();
                }
            };

        /* Convert to TimedCallable if timeout is specified. */
        long msecs = getTimeout();

        if (msecs != 0)
        {
            TimedCallable tc = new TimedCallable(function, msecs);
            tc.setThreadFactory(getThreadFactory());
            function = tc;
        }

        result.setter(function).run();
        SwingUtilities.invokeLater(doFinished);
    }


    /**
     * Starts the worker thread.
     */
    public synchronized void start()
    {
        if (thread == null)
        {
            thread = getThreadFactory().newThread(this);
        }

        thread.start();
    }


    /**
     * Wait at most msecs to access the constructed result.
     *
     * @param msecs DOCUMENT ME!
     *
     * @return current value
     *
     * @exception TimeoutException if not ready after msecs
     * @exception InterruptedException if current thread has been interrupted
     * @exception InvocationTargetException if the constructing thread encountered an
     *            exception or was interrupted.
     */
    public Object timedGet(long msecs)
      throws TimeoutException, InterruptedException, InvocationTargetException
    {
        return result.timedGet(msecs);
    }


    /**
     * Computes the value to be returned by the {@link #get} method.
     *
     * @return DOCUMENT ME!
     *
     * @throws Exception DOCUMENT ME!
     */
    protected abstract Object construct()
      throws Exception;


    /**
     * Called on the event dispatching thread (not on the worker thread) after the {@link
     * #construct} method has returned.
     */
    protected void finished()
    {
    }
}
