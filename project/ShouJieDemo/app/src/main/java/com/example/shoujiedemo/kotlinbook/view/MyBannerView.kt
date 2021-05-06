package com.example.shoujiedemo.kotlinbook.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import com.example.shoujiedemo.R
import kotlinx.coroutines.*
import kotlin.math.min

class MyBannerView : View {
    private var radius: Float = 20f                //view的圆角半径
    private var paint: Paint = Paint()
    private var screenHeight: Int = 0        //屏幕高度
    private var screenWidth: Int = 0        //屏幕宽度
    private var mWidth: Int = 0             //控件的宽度
    private var mHeight: Int = 0            //控件的高度
    private var imgs: MutableList<Bitmap>  = mutableListOf()     //轮播的Bitmap图片数组
    private var currentPosition: Int = 2    //当前轮播的图片的位置
    private val manager: WindowManager          //串口管理器
    private var isStart: Boolean = false        //线程开启判断
    private var currentLeft: Float = 0f        //当前中间图片的left
    private var currentTop: Float = 0f       //当前中间图片的top
    private var indexList: MutableList<Int> = mutableListOf(0,3,2,1,4)          //存储图片下标
    private var bitmapAndPxMap: MutableMap<Image,Bitmap> = mutableMapOf()     //存储图片的宽高
    lateinit var drawableArray: IntArray    //图片资源数组
    private var job: Job = Job()            //协程管理对象
    private var scope: CoroutineScope      //挂起函数的管理对象
    private var startToEndX = 0             //按下的起始位置用来判断滑动方向
    private var startX = 0                  //按下的其实位置用来计算滑动距离
    private var endX = 0                    //最后抬起位置
    private var dstX = 0                    //记录滑动距离里



    constructor(context: Context):this(context, null)

    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)


    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(
            context,
            attrs,
            defStyleAttr){

        val ta = context.obtainStyledAttributes(attrs, R.styleable.MyBannerView)
        radius = ta.getDimension(R.styleable.MyBannerView_bannerRadius,20f)
        scope = CoroutineScope(job)
        //获取屏幕高度和宽度
        manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
       if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            manager.currentWindowMetrics.apply {
                val bounds: Rect = this.bounds
                screenHeight = bounds.height()
                screenWidth = bounds.width()
            }
        } else {
            val dm = DisplayMetrics().apply {
                manager.getDefaultDisplay().getMetrics(this)
                screenWidth = this.widthPixels
                screenHeight = this.heightPixels
            }

        }

        ta.recycle()
    }


    override fun onDraw(canvas: Canvas?) {
        //只开一次协程
        if(!isStart) {
            //开启协程
            scope.async {
                bannerLaunch()
            }
            //遍历图片数组，将图片裁剪
            for(i in drawableArray.indices){
                var options = BitmapFactory.Options()
                options.inJustDecodeBounds = true
                BitmapFactory.decodeResource(resources,drawableArray[i],options)
                val bWidth = options.outWidth       //获取原图宽
                val bHeight = options.outHeight     //获取原图高
                val image = Image(bWidth,bHeight,drawableArray[i])   //图片宽高的对象存储
                val maxPx = image.imageMax()    //取最大的边
                var bitmap: Bitmap?
                options = BitmapFactory.Options().apply {
                    inScaled = true
                    inSampleSize = 2
                    inDensity = bWidth
                    inTargetDensity = mWidth * inSampleSize
                    inJustDecodeBounds = false
                }
                //判断原图的宽高是否大于控件的宽高
                if(maxPx >= mWidth || maxPx >= mHeight){
                    bitmap = BitmapFactory.decodeResource(resources,drawableArray[i],options)
                    Log.i("Bitmap的宽高","${bitmap.width} 和 ${bitmap.height}")
                    imgs.add(i,bitmap)
                    bitmapAndPxMap?.put(image,bitmap)
                }else{
                    bitmap = BitmapFactory.decodeResource(resources,drawableArray[i],options)
                    imgs.add(i,bitmap)
                    bitmapAndPxMap?.put(image,bitmap)
                }
            }
        }
        //裁剪出圆角
        val outerRect = RectF(0f,0f,mWidth.toFloat(),mHeight.toFloat())      //外部矩形
        val path = Path()
        path.addRoundRect(outerRect, radius, radius, Path.Direction.CW)
        //canvas?.drawFilter = PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        canvas?.clipPath(path)
        //绘制图片
        imgs?.let {
            canvas?.drawBitmap(imgs[indexList[0]],currentLeft - mWidth - 30,currentTop,null)
            canvas?.drawBitmap(imgs[indexList[1]],currentLeft,currentTop,null)
            canvas?.drawBitmap(imgs[indexList[2]],currentLeft + mWidth + 30,currentTop,null)
            canvas?.drawBitmap(imgs[indexList[3]],currentLeft + mWidth * 2 + 30,currentTop,null)
            canvas?.drawBitmap(imgs[indexList[4]],currentLeft + mWidth * 3 + 30,currentTop,null)
        }

        //绘制下标圆
        paint.style = Paint.Style.FILL_AND_STROKE
        val circleRadius = mWidth / 120 .toFloat()    //圆的半径
        var circleX = mWidth - 30 .toFloat()        //圆的x坐标
        var circleY = mHeight - 30 .toFloat()       //圆的Y坐标
        for(i in 0..2){
            if(i == currentPosition){
                paint.color =  Color.rgb(255, 255, 255)
                canvas?.drawCircle(circleX,circleY,circleRadius,paint)
            }else{
                paint.color = Color.argb(90,255, 255, 255)
                canvas?.drawCircle(circleX,circleY,circleRadius,paint)
            }
            circleX -= circleRadius*4
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //设置控件默认高度
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec))
        mWidth = measuredWidth      //获取控件本身宽度
        mHeight = measuredHeight       //获取控件本身高度
    }


    /**
     * 添加图片资源
     */
    fun addDrawable(drawableArray: IntArray){
        this.drawableArray = intArrayOf(0,0,0,0,0)
        this.drawableArray[0]= drawableArray[0]
        this.drawableArray[4] = drawableArray[2]
        for(i in 0..2){
            this.drawableArray[i+1] = drawableArray[i]
        }
    }


    /**
     *  绑定图片左移的协程与下标一起轮播
     */
    private suspend fun bannerLaunch() = scope.async {
        withContext(Dispatchers.Main){
            isStart = true
            while(true){
                delay(3000)
                currentPosition--
                if(currentPosition == -1)
                    currentPosition = 2
                //图片移动的子协程
                bannerCircle()
            }
        }
    }

    /**
     * 图片滚动的协程
     */
    private suspend fun bannerCircle() = scope.launch {
        withContext(Dispatchers.Main){
            while (currentLeft > 0 - (mWidth.toFloat())) {
                currentLeft -= 8
                delay(2)
                invalidate()
            }
             var t = 0
             t = indexList[1]
             indexList[0] = indexList[1]
             indexList[1] = indexList[2]
             indexList[2] = indexList[3]
             indexList[3] = t
             indexList[4] = indexList[1]
             currentLeft = 0f
        }
    }

    //对宽度进行判断
    private fun measureWidth(widthMeasureSpec: Int): Int {
        var resultWidth: Int
        //获取设置的测量模式和大小
        val specMode = MeasureSpec.getMode(widthMeasureSpec)
        val specSize = MeasureSpec.getSize(widthMeasureSpec)

        //如果是精确值模式，则宽度等于用户设置的宽度
        if (specMode == MeasureSpec.EXACTLY) {
            resultWidth = specSize
        } else {
            //否则，设置默认值，如果是最大值模式，则取用户设置的值和默认值中较小的一个
            resultWidth = screenWidth
            if (specMode == MeasureSpec.AT_MOST) {
                resultWidth = min(resultWidth, specSize)
            }
        }
        return resultWidth
    }

    //对高度进行判断
    private fun measureHeight(heightMeasureSpec: Int): Int {
        var resultHeight: Int
        val specMode = MeasureSpec.getMode(heightMeasureSpec)
        val specSize = MeasureSpec.getSize(heightMeasureSpec)
        if (specMode == MeasureSpec.EXACTLY) {
            resultHeight = specSize
        } else {
            resultHeight = screenHeight / 6
            if (specMode == MeasureSpec.AT_MOST) {
                resultHeight = min(resultHeight, specSize)
            }
        }
        return resultHeight
    }

    /**
     * 定义事件监听
     */
    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when(event?.action){
            //按下
            MotionEvent.ACTION_DOWN ->{
                //取消协程,暂停动画
                job.cancel()
                startToEndX = event.x.toInt()
                startX = event.x.toInt()
            }
            //移动
            MotionEvent.ACTION_MOVE ->{

                if(event.x >= startX){       //表示向右移动
                    dstX = event.x.toInt() - startX
                    Log.i("移动","向右移动$dstX")
                    currentLeft += dstX
                    invalidate()
                    startX = event.x.toInt()
                }else{                      //表示向左移动
                    Log.i("移动","向左移动")
                    dstX = startX - event.x.toInt()
                    Log.i("移动","向右移动$dstX")
                    currentLeft -= dstX
                    invalidate()
                    startX = event.x.toInt()
                }
                //最后抬起位置
                endX = event.x.toInt()
            }
            //抬起
            MotionEvent.ACTION_UP ->{
                job = Job()
                scope =CoroutineScope(job)
                Log.i("startX 和 endX","$startX 和 $endX")
                //判断最终向右滑还是向左滑
                when(endX > startToEndX){
                    true ->         //向左滑
                        if(currentLeft >= mWidth / 3){  //判断是否自动滑动到上一张
                            scope.launch {
                                while(currentLeft < mWidth){
                                    currentLeft += 8
                                    delay(2)
                                    invalidate()
                            }
                                val t: Int
                                t = indexList[3]
                                indexList[4] = indexList[3]
                                indexList[3] = indexList[2]
                                indexList[2] = indexList[1]
                                indexList[1] = t
                                indexList[0] = indexList[3]
                                currentLeft = 0f
                            }
                            //滑到负一格则调回第一个
                            if(++currentPosition == 3){
                                currentPosition = 0
                            }
                    }else{      //滑动手势不够而不进行自动滑动
                        scope.launch {
                            while(currentLeft > 0){
                                currentLeft -= 8
                                delay(2)
                                invalidate()
                            }
                            currentLeft = 0f
                        }

                    }
                    false ->        //向右滑
                        if(currentLeft < (0 - mWidth) / 3){//判断是否自动滑动到下一张
                        scope.launch {
                            while(currentLeft > (0 - mWidth)){
                                currentLeft -= 8
                                delay(2)
                                invalidate()
                            }
                            val t: Int
                            t = indexList[1]
                            indexList[0] = indexList[1]
                            indexList[1] = indexList[2]
                            indexList[2] = indexList[3]
                            indexList[3] = t
                            indexList[4] = indexList[1]
                            currentLeft = 0f
                        }
                        //滑到+1格则回到第一格
                        if(--currentPosition == -1){
                            currentPosition = 2
                        }
                    }else{//滑动手势不够而不进行自动滑动
                        scope.launch {
                            while(currentLeft < 0){
                                currentLeft += 8
                                delay(2)
                                invalidate()
                            }
                            currentLeft = 0f
                        }

                    }
                }
                scope.launch{
                    bannerLaunch()
                }
            }
            //事件取消
            MotionEvent.ACTION_CANCEL -> {
                if(job.isCancelled){
                    scope.launch{
                        bannerLaunch()
                    }
                }
            }
        }
        return true
    }

    /**
     * 回收bitmap
     */
    fun recycle(){
        bitmapAndPxMap.clear()
        imgs.forEach {
            if(!it.isRecycled)
                it.recycle()
        }
    }

    /**
     * 关闭协程
     */
    fun close(){
        job.cancel()
    }

}