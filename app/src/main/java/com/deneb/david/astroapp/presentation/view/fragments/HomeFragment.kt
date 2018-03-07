package com.deneb.david.astroapp.presentation.view.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.deneb.david.astroapp.BuildConfig

import com.deneb.david.astroapp.R
import com.deneb.david.astroapp.data.api.AstrobinApi
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast

class HomeFragment : Fragment() {
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val api = AstrobinApi.create()

        fun toastmsg(message:String){
            toast(message)
        }

        fun setImage(img:String){
            Glide.with(this).load(img).into(img_of_the_day)
        }

        fun setImageofTheDay(id:String ){
            val getOneImage = api.getOneImage(id,BuildConfig.ASTROBIN_KEY,BuildConfig.ASTROBIN_SECRET,"json")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            getOneImage.subscribe({
                response ->
                setImage((response as JsonObject).get("url_regular").asString)
            },
                    {
                        e -> e.printStackTrace()
                    })
        }


        val apiAstroBin = api.getImageOfTheDay("1",BuildConfig.ASTROBIN_KEY,BuildConfig.ASTROBIN_SECRET,"json")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        apiAstroBin.subscribe({
            response ->
            //toastmsg((response.asJsonObject.getAsJsonArray("objects").get(0) as JsonObject).get("image").asString)
            val uri = (response.asJsonObject.getAsJsonArray("objects").get(0) as JsonObject).get("image").asString
            val id  = uri.split("/")[4]
            setImageofTheDay(id)
                },
                {
                    e -> e.printStackTrace()

                },
                {
                  /*onComplete (notifyDatachange)*/
                })




        /*val getOneImage = api.getOneImage("330995",BuildConfig.ASTROBIN_KEY,BuildConfig.ASTROBIN_SECRET,"json")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        getOneImage.subscribe({
            response ->
            setImage((response as JsonObject).get("url_regular").asString)
        },
                {
                    e -> e.printStackTrace()
                })*/

        return view
    }

    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        fun newInstance(param1: String, param2: String): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}
