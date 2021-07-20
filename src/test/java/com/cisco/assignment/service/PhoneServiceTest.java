package com.cisco.assignment.service;

import com.cisco.assignment.dto.PhoneDto;
import com.cisco.assignment.mapper.PhoneMapper;
import com.cisco.assignment.model.Phone;
import com.cisco.assignment.repository.PhoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class PhoneServiceTest {

    @Mock
    private PhoneRepository phoneRepositoryMock;
    private PhoneService phoneServiceTest;


    @BeforeEach
    void setUp() {
        phoneServiceTest = new PhoneService(phoneRepositoryMock);
    }

    @Test
    void canSavePhone() {
        UUID phoneId = UUID.randomUUID();
        Phone phone = Phone.builder().phoneName("Prasad's Phone").phoneNumber("9086616656").phoneModel("Nokia").build();
        PhoneDto phoneDto = new PhoneDto(phoneId, "Prasad", "Prasad's Phone", "9086616656", "Nokia");

        try (MockedStatic<PhoneMapper> mockedStatic2 = Mockito.mockStatic(PhoneMapper.class)) {
            mockedStatic2.when(() -> PhoneMapper.phoneDtoToModel(any(PhoneDto.class))).thenReturn(phone);

            phoneServiceTest.savePhone(phoneDto);
            ArgumentCaptor<Phone> phoneDtoArgumentCaptor = ArgumentCaptor.forClass(Phone.class);
            verify(phoneRepositoryMock).save(phoneDtoArgumentCaptor.capture());
            Phone capturedUser = phoneDtoArgumentCaptor.getValue();
            assertThat(capturedUser).isEqualTo(phone);
        }
    }
}