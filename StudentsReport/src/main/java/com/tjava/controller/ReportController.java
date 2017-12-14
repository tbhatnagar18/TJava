package com.tjava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tjava.DAO.StudentDAO;
import com.tjava.model.Student;

@Controller
public class ReportController {

	@Autowired
	private StudentDAO studentDAOimpl;

	@PostMapping("/report")
	public ModelAndView postReport(@ModelAttribute("student") Student student) {

		ModelAndView view = new ModelAndView("report");

		return view;
	}

	@GetMapping("/report")
	public ModelAndView getReport(@ModelAttribute("student") Student student) {
		ModelAndView view = new ModelAndView("report");

		return view;
	}

}
