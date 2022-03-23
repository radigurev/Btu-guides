package btuguides.service.implementation;

import btuguides.models.binding.PartnersBindingModel;
import btuguides.models.entity.Partners;
import btuguides.repository.PartnerRepository;
import btuguides.service.PartnerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PartnersService implements PartnerService {

    private final PartnerRepository partnerRepository;
    private final ModelMapper modelMapper;
    public PartnersService(PartnerRepository partnerRepository, ModelMapper modelMapper) {
        this.partnerRepository = partnerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void savePartner(PartnersBindingModel map) {
        partnerRepository.save(modelMapper.map(map, Partners.class));
    }
}
