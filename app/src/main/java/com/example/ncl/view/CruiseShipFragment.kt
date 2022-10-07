package com.example.ncl.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.ncl.R
import com.example.ncl.data.remote.models.CruiseShipResponse
import com.example.ncl.databinding.FragmentCruiseShipBinding
import com.example.ncl.utils.BLISS
import com.example.ncl.utils.ESCAPE
import com.example.ncl.utils.SKY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CruiseShipFragment : Fragment() {

    private var _binding: FragmentCruiseShipBinding? = null
    private val binding: FragmentCruiseShipBinding get() = _binding!!
    private val cruiseShipViewModel by viewModels<CruiseShipViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCruiseShipBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initView()
    }

    private fun initObservers() {
        cruiseShipViewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Init -> Unit
                is State.Loading -> handleLoading(true)
                is State.Success -> {
                    handleLoading(false)
                    handleSuccess(state.data)
                }
                is State.Failure -> {
                    handleLoading(false)
                    handleError()
                }
            }

        }
    }

    private fun initView() {
        with(binding) {
            btnSkyInfo.setOnClickListener { cruiseShipViewModel.getCruiseShips(SKY) }
            btnBlissInfo.setOnClickListener { cruiseShipViewModel.getCruiseShips(BLISS) }
            btnEscapeInfo.setOnClickListener { cruiseShipViewModel.getCruiseShips(ESCAPE) }
        }
    }

    private fun handleLoading(isLoading: Boolean) {
        with(binding) {
            shipButtonGroup.isEnabled = !isLoading
            dataGroup.isVisible = !isLoading
            progressBar.isVisible = isLoading
            tvError.isVisible = false
        }
    }

    private fun handleSuccess(cruiseShip: CruiseShipResponse) = with(binding) {
        with(cruiseShip) {
            tvShipName.text = getString(R.string.ship_name).format(shipName)
            tvPassengerCapacity.text = getString(R.string.passenger_capacity)
                .format(shipFacts.passengerCapacity)
            tvCrewCount.text = getString(R.string.crew).format(shipFacts.crew)
            tvShipInauguralDate.text = getString(R.string.inaugural_date)
                .format(shipFacts.inauguralDate)
        }
    }

    private fun handleError() {
        with(binding) {
            dataGroup.isVisible = false
            tvError.isVisible = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}