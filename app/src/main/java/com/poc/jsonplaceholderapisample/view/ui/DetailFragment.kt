package com.poc.jsonplaceholderapisample.view.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.poc.jsonplaceholderapisample.databinding.FragmentDetailBinding
import com.poc.jsonplaceholderapisample.gateway.Resource
import com.poc.jsonplaceholderapisample.model.dto.PostDto
import com.poc.jsonplaceholderapisample.util.Util
import com.poc.jsonplaceholderapisample.view.adapters.PostListAdapter
import com.poc.jsonplaceholderapisample.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val TAG = "DetailFragment"
    private var _detailFragmentBinding: FragmentDetailBinding? = null
    private val detailFragmentBinding get() = _detailFragmentBinding!!
    private var userPostList: ArrayList<PostDto> = ArrayList()
    private lateinit var postListAdapter: PostListAdapter
    private var userId = -1

    val args: DetailFragmentArgs by navArgs()
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*   val onBackPressedCallback = object : OnBackPressedCallback(true) {
               override fun handleOnBackPressed() {
                   NavHostFragment.findNavController(this@DetailFragment).navigateUp()
               }
           }
           requireActivity().onBackPressedDispatcher.addCallback(
               this, onBackPressedCallback
           )*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _detailFragmentBinding = FragmentDetailBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return _detailFragmentBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userId = args.userId
        if (Util.isOnline(requireActivity())) {
            userViewModel.callUser(userId)
            observeUserDetailApi()
            observeUserPostResponse()
        } else {
            Toast.makeText(
                requireActivity(),
                "Please check your internet connection",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    private fun observeUserDetailApi() {
        userViewModel.mutableUserDetailLiveData.observe(viewLifecycleOwner, { response ->
            when (response) {

                is Resource.Success -> {
                    Log.d(TAG, response.data.toString())
                    detailFragmentBinding.progressBarDetailFragment.visibility = View.GONE
                    detailFragmentBinding.tvUserName.text = "User Name: ${response.data?.username}"
                    detailFragmentBinding.tvUserEmail.text = "Email: ${response.data?.email}"
                }

                is Resource.Error -> {
                    detailFragmentBinding.progressBarDetailFragment.visibility = View.GONE
                    Log.d(TAG, response.data.toString())
                    Toast.makeText(requireActivity(), "Something went wrong", Toast.LENGTH_SHORT)
                        .show()
                }

                is Resource.Loading -> {
                    detailFragmentBinding.progressBarDetailFragment.visibility = View.VISIBLE
                }

            }
        })
    }

    private fun observeUserPostResponse() {
        userViewModel.mutablePostLiveData.observe(viewLifecycleOwner, { response ->
            when (response) {

                is Resource.Success -> {
                    for (res in response.data!!) {
                        if (res.userId == userId) {
                            userPostList.add(PostDto(res.title, res.body))
                        }
                    }
                    postListAdapter = PostListAdapter(userPostList)
                    detailFragmentBinding.rvDetailFragment.adapter = postListAdapter
                    Log.d(TAG, response.data.toString())
                }

                is Resource.Error -> {

                }

                is Resource.Loading -> {

                }

            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _detailFragmentBinding = null
    }

}