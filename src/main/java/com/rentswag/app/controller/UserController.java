package com.rentswag.app.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rentswag.app.config.TokenProvider;
import com.rentswag.app.dao.UserDao;
import com.rentswag.app.model.AuthToken;
import com.rentswag.app.model.LoginUser;
import com.rentswag.app.model.Order;
import com.rentswag.app.model.Product;
import com.rentswag.app.model.User;
import com.rentswag.app.model.UserDto;
import com.rentswag.app.service.impl.OrderServiceImpl;
import com.rentswag.app.service.impl.ProductServiceImpl;
import com.rentswag.app.service.impl.UserServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

//    @Autowired
//    private UserService userService;
    @Autowired
    private UserServiceImpl userserviceimpl;
    @Autowired
    private ProductServiceImpl productimpl;
    @Autowired
    private OrderServiceImpl orderserviceImpl;
    @Autowired
    private UserDao userdao;
   

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> generateToken(@RequestBody LoginUser loginUser) throws AuthenticationException {
    	User usr=userdao.findByUsername(loginUser.getUsername());
    	if(usr == null) {
    		return new ResponseEntity<>("Invalid username", HttpStatus.FORBIDDEN);
    	}
//    	if(usr.getPassword().equals(loginUser.getPassword())) {
//    		return new ResponseEntity<>("Invalid password", HttpStatus.NOT_FOUND);
//    	}
    	if(usr.isEnabled()) {
    		final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginUser.getUsername(),
                            loginUser.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final String token = jwtTokenUtil.generateToken(authentication);
            return ResponseEntity.ok(new AuthToken(token));		
    	}
        
    	return new ResponseEntity<>("Not Verified Verify your email", HttpStatus.UNAUTHORIZED);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/adminping", method = RequestMethod.GET)
    public String adminPing(){
        return "Only Admins Can Read This";
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value="/userping", method = RequestMethod.GET)
    public String userPing(){
       return "Any User Can Read This";
   
    }
 
    @RequestMapping(value="/getallproduct", method = RequestMethod.GET)
    public List<Product>  findproductbtid(){
       return productimpl.listOrderInfo();
   
    }
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/getallorder", method = RequestMethod.GET)
    public List<Order> findallOrder(){
       return orderserviceImpl.listOrderInfo();
   
    }
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/addproducts", method = RequestMethod.POST)
    public List<Product> saveProducts(@RequestBody List<Product> productList ){
        return productimpl.saveAllProducts(productList);
    }
    
    @RequestMapping(value="/listOfOrder", method = RequestMethod.POST)
    public List<Order> saveListOfOrder(@RequestBody  List<Order> orderList ){
        return orderserviceImpl.saveAllOrders(orderList);
    }
    @RequestMapping(value="/addtoOrder", method = RequestMethod.POST)
    public Order saveOrder(@RequestBody Order order ){
        return orderserviceImpl.saveOrder(order);
    }
    
    @RequestMapping(value="/fetchbyusername/{username}", method = RequestMethod.GET)
    public User fetchuserbyusername(@PathVariable String username ){
		return userdao.findByUsername(username); 
    
  }
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/fetchallUserAndAdmin", method = RequestMethod.GET)
    public List<User> fetchalluser(){
       return userserviceimpl.findAll();
  }
    
    @PostMapping("/register")
    public  ResponseEntity<?>  processRegister(@RequestBody   UserDto user, HttpServletRequest request)
            throws UnsupportedEncodingException, MessagingException {
    	User tempuser=userdao.findByUsername(user.getUsername());
    	if(tempuser != null) {
    		if(user.getUsername().equals(tempuser.getUsername()) &&  user.getEmail().equals(tempuser.getEmail()))
        	{
        		return new ResponseEntity<>("username already used", HttpStatus.UNAUTHORIZED);
        	}
    	}
    		System.out.println(user.getUserFromDto());
    	userserviceimpl.register(user, getSiteURL(request));       
    	return new ResponseEntity<>("Registered Sucessfully", HttpStatus.OK);
    }
     
    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }  
    
    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (userserviceimpl.verify(code)) {
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }
    
}