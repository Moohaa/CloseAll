package org.close_all.project.platform

import java.io.File
import java.io.RandomAccessFile
import java.nio.channels.FileChannel
import java.nio.channels.FileLock


object ThisAppInstanceLock {
    var lock: FileLock? = null
    var lockStream: FileChannel? = null
    var lockFile: File? = null

    fun requireFileLock(): Boolean {
        lockFile = File("/tmp/closeAll/app.lock")
        lockStream = RandomAccessFile(lockFile, "rw").channel
        lock = lockStream?.tryLock()

        return lock != null
    }

    fun releaseFileLock() {
        lock?.release()
        lockStream?.close()
        lockFile?.delete()
    }

}