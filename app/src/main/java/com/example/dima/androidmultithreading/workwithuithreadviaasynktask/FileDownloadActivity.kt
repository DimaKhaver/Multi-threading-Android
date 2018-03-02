package com.example.dima.androidmultithreading.workwithuithreadviaasynktask

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import java.io.IOException
import java.io.InputStream
import java.net.MalformedURLException
import java.net.URL


/*
This example shows the implementation of an Activity that displays four images that
are downloaded from the network with an AsyncTask . The UI consists of a determinate
progress bar—an mProgressBar —that shows the number of downloaded images) and
a layout—using mLayoutImages —whose children constitute the downloaded images.
The download starts upon Activity creation and is cancelled on destruction.

AsyncTask executes tasks async, multiple tasks can be executed either sequentially or concurrently.

• Because AsyncTask has a global execution environment, the more tasks you execute
with an AsyncTask , the higher the risk that tasks will not be processed as expected
because there are other tasks in the application that hold the execution environment.
• Inconsistency in execution environments over different platform versions makes
it more difficult to either optimize execution for performance (concurrent execution)
or thread safety (sequential execution)
*/
class FileDownloadActivity : Activity() {

    companion object {
      // URLs of the images.
        private val DOWNLOAD_URLS = Array<String>(4) {
            "http://developer.android.com/design/media/devices_displays_density@2x.png";
            "http://developer.android.com/design/media/iconography_launcher_example2.png";
            "http://developer.android.com/design/media/iconography_actionbar_focal.png";
            "http://developer.android.com/design/media/iconography_actionbar_colors.png"
        }
    }

    public var fileDownloadTask: DownloadTask? = null
    public var mProgressBar: ProgressBar? = null
    public var mLayoutImages: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //setContentView(R.layout.activity_file_download);
      //mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
      //mProgressBar!!.setMax(DOWNLOAD_URLS.size);
      //mLayoutImages = (LinearLayout) findViewById(R.id.layout_images);
      //fileDownloaderTask = new DownloadTask(this);
      //fileDownloaderTask.execute(DOWNLOAD_URLS); // Pass the URLs to doInBackground
    }

    override fun onDestroy() {
        super.onDestroy()
/*
        When the Activity is destroyed, the AsyncTask is cancelled and its reference
        to the destroyed Activity is nullified so that it can be garbage collected, although
        the worker thread is alive.

        fileDownloadTask?.setActivity(null)
        fileDownloadTask?.cancel(true)
*/
    }
  /*
    Definition of AsyncTask : An array of String objects is passed to doInBack
    ground, and Bitmap objects are returned during background execution. The background
    thread does not pass a result to the UI thread, so the last parameter is declared Void
  */
  class DownloadTask : AsyncTask<String, Bitmap, Void> {
      //The Activity is referenced for updates on the UI thread.
      @SuppressLint("StaticFieldLeak")
      private var activity: FileDownloadActivity? = null
      private var mCount = 0

      constructor(activity: FileDownloadActivity) {
          this.activity = activity
      }

      override fun onPreExecute() {
          super.onPreExecute()
        //Show the progress bar before the background task executes
          activity!!.mProgressBar!!.visibility = View.VISIBLE;
          activity!!.mProgressBar!!.progress = 0;

      }

      override fun doInBackground(vararg params: String?): Void? {
          for (url in params) {
              if (!isCancelled) {
                  val bitmap = downloadFile(url!!)
                  publishProgress(bitmap)
              }
          }
          return null
      }

      override fun onProgressUpdate(vararg bitmaps: Bitmap) {
          super.onProgressUpdate(*bitmaps)
          if (activity != null) {
              activity?.mProgressBar?.progress = ++mCount
              val iv = ImageView(activity)
              iv.setImageBitmap(bitmaps[0])
              activity?.mLayoutImages?.addView(iv)
          }
      }

      override fun onPostExecute(result: Void?) {
          super.onPostExecute(result)
          if (activity != null)
              activity!!.mProgressBar!!.visibility = View.GONE;
      }

      override fun onCancelled() {
          super.onCancelled()
          if (activity != null)
              activity?.mProgressBar?.setVisibility(View.GONE);
      }

      private fun downloadFile(url: String): Bitmap? {
          var bitmap: Bitmap? = null
          try {
              bitmap = BitmapFactory
                      .decodeStream(URL(url)
                              .content as InputStream)
          } catch (e: MalformedURLException) {
              e.printStackTrace()
          } catch (e: IOException) {
              e.printStackTrace()
          }
          return bitmap
      }
    }
}