package com.mnemo.samantha.data.file_storage

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import java.io.File
import java.io.FileOutputStream


class FileStorage(context: Context){

    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)


    companion object{

        @Volatile
        private lateinit var INSTANCE: FileStorage

        fun getInstance(context: Context) : FileStorage{

            synchronized(this){

                if (!::INSTANCE.isInitialized){
                    INSTANCE = FileStorage(context)
                }

                return INSTANCE
            }
        }


    }

    fun saveClientAvatar(bitmap: Bitmap, clientId: Long){
        val fileName = "cl${clientId}.JPEG"
        val file = File(storageDir, fileName)

        val fileOutPutStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fileOutPutStream)
        fileOutPutStream.flush()
        fileOutPutStream.close()
    }

    fun getClientAvatarPath(clientId: Long) : File{
        val fileName = "cl${clientId}.JPEG"
        return File(storageDir, fileName)
    }

    fun saveMasterAvatar(bitmap: Bitmap, masterId: Long){
        val fileName = "m${masterId}.JPEG"
        val file = File(storageDir, fileName)

        val fileOutPutStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fileOutPutStream)
        fileOutPutStream.flush()
        fileOutPutStream.close()
    }

    fun getMasterAvatarPath(masterId: Long) : File{
        val fileName = "m${masterId}.JPEG"
        return File(storageDir, fileName)
    }
}