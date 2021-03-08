package com.poc.jsonplaceholderapisample.view.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.poc.jsonplaceholderapisample.databinding.FragmentHomeBinding
import com.poc.jsonplaceholderapisample.gateway.Resource
import com.poc.jsonplaceholderapisample.model.dto.UserDto
import com.poc.jsonplaceholderapisample.util.Util
import com.poc.jsonplaceholderapisample.view.adapters.UserListAdapter
import com.poc.jsonplaceholderapisample.view.listeners.PostViewClickListener
import com.poc.jsonplaceholderapisample.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), PostViewClickListener {

    private val TAG = "HomeFragment"
    private var _homeBinding: FragmentHomeBinding? = null
    private val homeBinding get() = _homeBinding!!
    private val userViewModel: UserViewModel by activityViewModels()
    private lateinit var userListAdapter: UserListAdapter
    private var postList = ArrayList<UserDto>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return _homeBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG,"onViewCreated")
        if (Util.isOnline(requireActivity())) {
            userViewModel.callUserListApi()
            observeUserListApiResponse()
        } else {
            Toast.makeText(
                requireActivity(),
                "Please check your internet connection",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    private fun observeUserListApiResponse() {
        userViewModel.mutablePostLiveData.observe(viewLifecycleOwner, { response ->
            when (response) {

                is Resource.Success -> {
                    postList.clear()
                    homeBinding.progressBarHomeFragment.visibility = View.GONE
                    var hashMap = HashMap<Integer, Integer>()
                    for (post in response.data!!) {
                        if (hashMap.containsKey(post.userId)) {
                            var userPost: Int = hashMap.get(post.userId)!!.toInt()
                            hashMap[Integer(post.userId)] = Integer(++userPost)
                        } else {
                            hashMap[Integer(post.userId)] = Integer(1)
                        }
                    }
                    hashMap.forEach {
                        postList.add(UserDto(it.key.toInt(), it.value.toInt()))
                    }
                    userListAdapter = UserListAdapter(postList, this)
                    homeBinding.rvHomeFragment.adapter = userListAdapter
                    Log.d(TAG, response.data.toString())
                }

                is Resource.Error -> {
                    homeBinding.progressBarHomeFragment.visibility = View.GONE
                    Log.d(TAG, response.data.toString())
                    Toast.makeText(requireActivity(), "Something went wrong", Toast.LENGTH_SHORT)
                        .show()
                }

                is Resource.Loading -> {
                    homeBinding.progressBarHomeFragment.visibility = View.VISIBLE
                }

            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _homeBinding = null
        Log.d(TAG,"onDestroy")
    }

    override fun postClicked(userId: Int) {
        val action = HomeFragmentDirections.actionNavigateHomeFragmentToDetailFragment(
            userId
        )
        Navigation.findNavController(homeBinding.root).navigate(action)
    }

}