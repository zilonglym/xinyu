package com.graby.store.portal.util;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xml.internal.security.utils.Base64;

public class TpTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
/*
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String templateType = request.getParameter("type");
			String output = request.getParameter("output");
			List waybillDtoList = (List) new ObjectMapper().readValue(request.getInputStream(), new TypeReference() {
			});
			response.setCharacterEncoding("UTF-8");
			boolean k = false;
			if (waybillDtoList.size() <= 0) {
				response.getWriter().println(getPrintResultResponse(k));
			} else if ((StringUtils.isEmpty(((WaybillDto) waybillDtoList.get(0)).getAppId()))
					|| (StringUtils.isEmpty(((WaybillDto) waybillDtoList.get(0)).getAppKey()))) {
				response.getWriter().println(getPrintResultResponse(k));
			} else {
				if ((waybillDtoList.size() > 0)
						&& (StringUtils.isNotEmpty(((WaybillDto) waybillDtoList.get(0)).getAppId()))) {
					if (StringUtils.isNotEmpty(((WaybillDto) waybillDtoList.get(0)).getAppKey())) {
						String appId = ((WaybillDto) waybillDtoList.get(0)).getAppId();
						String appKey = ((WaybillDto) waybillDtoList.get(0)).getAppKey();
						String getMailAddress = ((WaybillDto) waybillDtoList.get(0)).getConsignerAddress();

						if ((validAppid(appId)) && (validAppkey(appKey)) && (validAddressSize(getMailAddress))) {
							List waybillPrintDtoList = WaybillUtils.initWaybillPrintDtoList(waybillDtoList);
							WaybillPrintDS waybillPrintDS = new WaybillPrintDS();

							waybillPrintDS.setWayBillList(waybillPrintDtoList);
							JasperReport jsperReport = (JasperReport) JRLoader.loadObject(new File(request.getSession()
									.getServletContext().getRealPath("/template/" + templateType + ".jasper")));
							JasperPrint jasperPrint = JasperFillManager.fillReport(jsperReport, new HashMap(),
									waybillPrintDS);

							if ((output == null) || ("".equals(output)) || ("print".equals(output))) {
								boolean isPrintSuccess = JasperPrintManager.printReport(jasperPrint, true);
								response.getWriter().println(getPrintResultResponse(isPrintSuccess));
								return;
							}
							if (!"image".equals(output))
								return;
							int pageSize = jasperPrint.getPages().size();
							WaybillPrintResponseDto resp = new WaybillPrintResponseDto();
							List imageList = new ArrayList();
							resp.setCode("EX_CODE_OPENAPI_0200");
							for (int index = 0; index < pageSize; index++) {
								Image image = JasperPrintManager.printPageToImage(jasperPrint, index, 4.0F);
								String imageStr = getPrintImageResponse(toBufferedImage(image));
								if (!"".equals(imageStr)) {
									imageList.add(imageStr);
								}
							}
							resp.setResult(imageList);
							response.getWriter().println(new ObjectMapper().writeValueAsString(resp));

							return;
						}
						response.getWriter().println(getPrintResultResponse(k));

						return;
					}
				}
				response.getWriter().println(getPrintResultResponse(k));
			}
		} catch (JRException e) {
			response.getWriter().println(getExceptionResponse(e));
		}
	}

	private static String getPrintResultResponse(boolean isSuccess) throws JsonProcessingException {
		WaybillPrintResponseDto resp = new WaybillPrintResponseDto();
		if (isSuccess) {
			resp.setCode("EX_CODE_OPENAPI_0200");
			resp.setResult("电子运单打印成功!");
		} else {
			resp.setCode("EX_CODE_OPENAPI_0500");
			resp.setResult("电子运单打印失败!");
		}
		return new ObjectMapper().writeValueAsString(resp);
	}

	private String getPrintImageResponse(BufferedImage image) throws IOException {
		if (image != null) {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(image, "png", out);
			byte[] imageByte = out.toByteArray();
			String imageStr = new String(Base64.encode(imageByte));
			return imageStr;
		}
		return "";
	}

	private String getExceptionResponse(Exception e) {
		try {
			WaybillPrintResponseDto resp = new WaybillPrintResponseDto();
			resp.setCode("EX_CODE_OPENAPI_0500");
			resp.setResult("系统异常：" + e);
			return new ObjectMapper().writeValueAsString(resp);
		} catch (JsonProcessingException localJsonProcessingException) {
		}
		return "error";
	}

	public BufferedImage toBufferedImage(Image image) {
		if ((image instanceof BufferedImage)) {
			return (BufferedImage) image;
		}
		image = new ImageIcon(image).getImage();
		BufferedImage bimage = null;
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			int transparency = 1;

			GraphicsDevice gs = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gs.getDefaultConfiguration();
			bimage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), transparency);
		} catch (HeadlessException localHeadlessException) {
		}
		if (bimage == null) {
			int type = 12;

			bimage = new BufferedImage(image.getWidth(null) * 4, image.getHeight(null) * 4, type);
		}

		Graphics g = bimage.createGraphics();

		g.drawImage(image, 0, 0, null);
		g.dispose();

		return bimage;
	}

	private static boolean validAppid(String str) {
		boolean flag = false;

		Pattern pattern = Pattern.compile("^[0-9]{8}$");
		Matcher matcher = pattern.matcher(str);
		flag = matcher.matches();

		return flag;
	}

	private static boolean validAppkey(String str) {
		boolean flag = false;

		Pattern pattern = Pattern.compile("^[A-Z0-9]{32}$");
		Matcher matcher = pattern.matcher(str);
		flag = matcher.matches();

		return flag;
	}

	private static boolean validAddressSize(String str) {
		boolean flag = false;
		if (str.length() < 24) {
			flag = true;
		}
		return flag;
	}
	*/
}