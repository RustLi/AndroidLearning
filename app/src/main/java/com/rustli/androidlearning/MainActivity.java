package com.rustli.androidlearning;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.rustli.androidlearning.fragment_recycler.ItemFragment;
import com.rustli.androidlearning.fragment_recycler.dummy.DummyContent;
import com.rustli.libcommon.ThreadUtil;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public class MainActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener {
    private static final String TAG = "lwl";
    EditText etNum;
    Button btOk;
    private static final ExecutorService cacheThreadPool;
    private static int maxCount = 0;

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = Math.max(4, Math.min(CPU_COUNT - 1, 8));
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE_SECONDS = 0;
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "lwl customThreadPool #" + mCount.getAndIncrement());
        }
    };

    private static final BlockingQueue<Runnable> sPoolWorkQueue =
            new LinkedBlockingQueue<Runnable>();

    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
                sPoolWorkQueue, sThreadFactory);
//        threadPoolExecutor.allowCoreThreadTimeOut(true);
//        cacheThreadPool = threadPoolExecutor;

//        cacheThreadPool = Executors.newCachedThreadPool();
        cacheThreadPool = Executors.newFixedThreadPool(CPU_COUNT + 1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: lwl cpu = " + CPU_COUNT + " core = " + CORE_POOL_SIZE + " max = " + MAXIMUM_POOL_SIZE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etNum = findViewById(R.id.et_num);
        btOk = findViewById(R.id.bt_ok);

        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = etNum.getText().toString();
                int numValue = Integer.parseInt(num);
                Log.d(TAG, "onClick: numValue = " + numValue);
                try {
                    cacheThreadPoolTest(numValue);
                }catch (Exception e){
                    e.printStackTrace();
                    Log.e(TAG, "onClick: e = " + e.toString() );
                }
            }
        });
//        if (savedInstanceState == null) {
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            ItemFragment fragment = new ItemFragment();
//            transaction.replace(R.id.fragment_container, fragment);
//            transaction.commit();
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
    }

    public String jUnitTest() {
        return "jUnitTest";
    }

    private int maxPoolCount = 0;
    private void cacheThreadPoolTest(int num) {
        Log.d(TAG, "cacheThreadPoolTest: num = " + num);
        final long cacheTime = System.currentTimeMillis();
        final int length = num;
        for (int i = 0; i < length; i++) {
            final int index = i;
            cacheThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    System.out.println("线程：" + threadName + ",正在执行第" + index + "个任务");
                    ThreadUtil.sleepRandom(40, 80);
                    Log.d(TAG, "run: cacheThreadPool = " + cacheThreadPool);
                    ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor)cacheThreadPool;
                    int activeCount = threadPoolExecutor.getActiveCount();
                    long completedCount = threadPoolExecutor.getCompletedTaskCount();
                    if (activeCount > maxPoolCount){
                        maxPoolCount = activeCount;
                    }
                    if (completedCount == length - 1 ){
                        Log.d(TAG, "run: cacheTime = " + (System.currentTimeMillis() - cacheTime) + " maxActiveCount = " + maxPoolCount);
                    }
                }
            });
        }
        Log.d(TAG, "总数  ：：：： run: cacheThreadPool = " + cacheThreadPool);
    }
}
