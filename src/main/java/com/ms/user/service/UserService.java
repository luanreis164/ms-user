package com.ms.user.service;

import com.ms.user.client.viacep.ViacepClient;
import com.ms.user.client.viacep.dto.ViaCepAddress;
import com.ms.user.exception.CepValidationException;
import com.ms.user.exception.EmailValidationException;
import com.ms.user.exception.UserNotFoundException;
import com.ms.user.model.Address;
import com.ms.user.model.User;
import com.ms.user.producer.UserProducer;
import com.ms.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ViacepClient viacepClient;
    private final UserProducer userProducer;

    public UserService(UserRepository userRepository, ViacepClient viacepClient, UserProducer userProducer) {
        this.userRepository = userRepository;
        this.viacepClient = viacepClient;
        this.userProducer = userProducer;
    }

    @Transactional
   public User save(User userModel,Address address){
        checkUserCep(userModel);
        checkUserEmailAlreadyCreated(userModel);
        setUserAddress(userModel,address);

        userModel = userRepository.save(userModel);
        userProducer.publishMessageEmail(userModel);
        return userModel;
    }

    private void setUserAddress(User userModel,Address address) {
        userModel.getAddresses().add(viacepAddressToUserAddress(viacepClient.getAddress(userModel.getCep()),address,userModel));
    }

    private Address viacepAddressToUserAddress(ViaCepAddress viaCepAddress,Address address,User user) {
            address.setCep(viaCepAddress.cep());
            address.setCity(viaCepAddress.localidade());
            address.setStreet(viaCepAddress.logradouro());
            address.setState(viaCepAddress.uf());
            address.setNeighborhood(viaCepAddress.bairro());
            address.setUser(user);
            return address;

    }


    private static void checkUserCep(User userModel) {
        if (!isCepValid(userModel.getCep())) {
            throw new CepValidationException("CEP inválido. Formato esperado: XXXXX-XXX.");
        }
    }

    private void checkUserEmailAlreadyCreated(User userModel) {
        if (userRepository.existsByEmail(userModel.getEmail()))
            throw new EmailValidationException("Email já cadastrado: " + userModel.getEmail());
    }

    public static boolean isCepValid(String cep) {
        final String CEP_PATTERN = "\\d{5}-\\d{3}";
        Pattern pattern = Pattern.compile(CEP_PATTERN);
        Matcher matcher = pattern.matcher(cep);
        return matcher.matches();
    }

    public User findById(UUID id) {
        return userRepository.findByIdAndStatusIsTrue(id).orElseThrow(UserNotFoundException::new);
    }

    public List<User> findAll() {
        return userRepository.findAllByStatusIsTrue();
    }

    public void activateUser(UUID id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        user.setStatus(true);
        userRepository.save(user);
    }

    public void inactivateUser(UUID id) {
        User user = userRepository.findByIdAndStatusIsTrue(id).orElseThrow(UserNotFoundException::new);
        user.setStatus(false);
        userRepository.save(user);
    }
}
