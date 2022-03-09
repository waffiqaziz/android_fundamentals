package com.dicoding.fundamental.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.DialogFragment
import com.dicoding.fundamental.R


class OptionDialogFragment : DialogFragment() {

  private lateinit var btnClose: Button
  private lateinit var btnChoose: Button
  private lateinit var rgOptions: RadioGroup
  private lateinit var rgSaf: RadioButton
  private lateinit var rgMou: RadioButton
  private lateinit var rgLvg: RadioButton
  private lateinit var rgMoyes: RadioButton
  private var optionDialogListener: OnOptionDialogListener? = null

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_option_dialog, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    btnChoose = view.findViewById(R.id.btn_choose)
    btnClose = view.findViewById(R.id.btn_close)
    rgOptions = view.findViewById(R.id.rg_option)
    rgSaf = view.findViewById(R.id.rb_saf) // Sir Alex Ferguson
    rgMou = view.findViewById(R.id.rb_jm) // Jose Mourihno
    rgLvg = view.findViewById(R.id.rb_lvg) // Louis van Gaal
    rgMoyes = view.findViewById(R.id.rb_dm) // David Moyes

    btnChoose.setOnClickListener {
      val checkedRadioButtonId = rgOptions.checkedRadioButtonId
      if (checkedRadioButtonId != -1) {
        var coach: String? = when (checkedRadioButtonId) {
          R.id.rb_saf -> rgSaf.text.toString().trim()
          R.id.rb_jm -> rgMou.text.toString().trim()
          R.id.rb_lvg -> rgMou.text.toString().trim()
          R.id.rb_dm -> rgMoyes.text.toString().trim()
          else -> null
        }
        optionDialogListener?.onOptionChosen(coach)
        dialog?.dismiss()
      }
    }

    btnClose.setOnClickListener { dialog?.cancel() }
  }

  interface OnOptionDialogListener {
    fun onOptionChosen(text: String?)
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    val fragment = parentFragment
    if (fragment is DetailCategoryFragment) {
      this.optionDialogListener = fragment.optionDialogListener // frp, Category fragment
    }
  }

  override fun onDetach() {
    super.onDetach()
    this.optionDialogListener = null
  }
}