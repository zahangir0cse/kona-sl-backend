package com.zahangir.konasl.controller;

import com.zahangir.konasl.annotations.ApiController;
import com.zahangir.konasl.dto.Response;
import com.zahangir.konasl.service.ItemService;
import com.zahangir.konasl.utils.UrlConstraint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ApiController
@RequestMapping(UrlConstraint.DashboardManagement.ROOT)
public class DashboardController {

    private final ItemService itemService;

    public DashboardController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping(UrlConstraint.DashboardManagement.GET_ALL)
    public Response getAll(HttpServletRequest request, HttpServletResponse response){
        return itemService.getAllItems();
    }
}
