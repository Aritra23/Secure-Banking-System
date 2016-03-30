 <%@ page import="java.util.List" %>
 <%@ page import="com.asu.cse.model.SSProfileTransaction" %>
 <%@ page import="com.asu.cse.model.SSUser" %>
 <div class="row">     
  <div class="col-md-6 col-md-offset-3">
    
      <table class="striped">
        <thead>
          <tr>
              <th data-field="">Transaction Id</th>
              <th data-field=""> </th>
              <th data-field="">Authorized</th>
               </tr>
        </thead>
       
         </table>
         
       <c:if test="${error=='false'}">
  	 	<div class="row"> </div>
  	 	<h5 class="text-center">${errorMessage}</h5>
     	<div class="row"> </div>
     	</c:if>  
     	<!--    	Object Transaction = (Object)request.getAttribute("transactionRecur"); -->	
     <ul class="collapsible" data-collapsible="accordion">
     <%final Integer recur = (Integer)request.getAttribute ("recur");
     List<SSProfileTransaction> Transaction = (List<SSProfileTransaction>) request.getAttribute("transactionRecur");	
     List<SSUser> User = (List<SSUser>) request.getAttribute("UserList");	
     for(int i=0;i<recur;i++){%>
    	<li>
      <div class="collapsible-header"><span style="margin-left:2pc"></span><%out.print(Transaction.get(i).getProfileTransactionId());%>  <span style="margin-left:16pc"></span>
      <a href="#">Old values</a> <span style="margin-left:13pc"></span><a href="#">New Values</a></div>
      <div class="collapsible-body">
      
	
      
      <%--Dynamic page loading should be created so as to  --%>
       <table class="striped">
        
          
               <tr>
               	<td><span style="margin-left:3pc"></span> First Name:</td>
               	<td><% out.print(User.get(i).getFirstName());%></td>
               	<td><% out.print(Transaction.get(i).getFirstname());%>  </td>
               </tr>
               <tr>
               	<td><span style="margin-left:3pc"></span>Address: </td>
               	<td><% out.print(User.get(i).getLastName());%></td>  
               	<td><% out.print(Transaction.get(i).getLastname());%> </td>
               	             
               </tr>
               <tr>
               	<td><span style="margin-left:3pc"></span>City: </td>
               	<td><% out.print(User.get(i).getCity());%></td>  
               	<td><% out.print(Transaction.get(i).getCity());%>  </td>
                            
               </tr>
               <tr>
               	<td><span style="margin-left:3pc"></span>Email </td>
               	<td> <% out.print(User.get(i).getEmail());%> </td>  
               	<td><% out.print(Transaction.get(i).getEmail());%> </td>             	             
               </tr>
               <tr>
               	<td><span style="margin-left:3pc"></span>Phone number:</td>
               	<td><% out.print(User.get(i).getPhoneNumber());%></td>  
               	<td><% out.print(Transaction.get(i).getPhoneNumber());%>  </td>
               </tr>
               <tr>
               	<td><span style="margin-left:3pc"></span>Address:</td>
               	<td><% out.print(User.get(i).getAddress());%> </td>  
               	<td><% out.print(Transaction.get(i).getAddress());%></td>            
               </tr>
               <tr>
               	<td><span style="margin-left:3pc"></span>State:</td>
               	<td><% out.print(User.get(i).getState());%> </td>  
               	<td><% out.print(Transaction.get(i).getState());%></td>            
               </tr>
               <tr>
               	<td><span style="margin-left:3pc"></span>User Name:</td>
               	<td><% out.print(User.get(i).getUserName());%> </td>  
               	<td><% out.print(Transaction.get(i).getUserName());%></td>            
               </tr>
               <tr>
               	<td><span style="margin-left:3pc"></span>Country:</td>
               	<td><% out.print(User.get(i).getCountry());%> </td>  
               	<td><% out.print(Transaction.get(i).getCountry());%></td>            
               </tr>
               </table>
               <form:form commandName="transactionId" action="${PostRequest}" method="POST" modelAttribute="transactionId">
               <input type="hidden" name="tranId1" value="<%out.print(Transaction.get(i).getProfileTransactionId());%>" />
                <input type="hidden" name="userId1" value="<%out.print(User.get(i).getUserid());%>" />
               <table>
               <tr>	
               		                	<td>
                	</td>
                	<td>
                	<div class="dropdown">
  					  <select class="form-control" id="BoolVal" name="BoolVal">
        				<option value=true>Approve</option>
        				<option value=false>Decline</option>
      				</select>
 					 </div>
                	
                	</td>
                	<td>
                	</td>
                	<td>
                	</td>
                	<td>
                	 <button id="submit" class="btn waves-effect waves-light" type="submit" name="action">Submit
    				  <i class="material-icons right">send</i>
  					 </button>
  					</td>
  					
          		
        		</tr>
               <%--Script should be inserted here to check weather the text box is filled before approving or not --%>
               
            
       </table>
       </form:form>
       
      </div>
    </li>
   <%} %>
    </ul>
    
     </div>
 </div>      
       
       