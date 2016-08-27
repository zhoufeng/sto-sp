package com.shenma.top.imagecopy.util.scheduling;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shenma.aliutil.exception.AliReqException;
import com.shenma.top.imagecopy.controller.AdminController;

@Component
public class CateAutoJob {
	@Autowired
	private AdminController adminController;
	protected void execute() throws IOException, AliReqException, InterruptedException {
		adminController.updateAll();
	}
}
