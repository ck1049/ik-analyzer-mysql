package org.wltea.analyzer.dic;

/**
 * MySQL词库监控
 * @author loafer
 * @since 2024-05-04 17:54:03
 **/
public class MySQLMonitor implements Runnable {

    @Override
    public void run() {
        // 重新加载词典
        Dictionary.getSingleton().reLoadMainDict();
    }
}
