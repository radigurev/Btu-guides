package btuguides.service.implementation;

import btuguides.models.binding.PartnersBindingModel;
import btuguides.models.entity.Partners;
import btuguides.models.view.TableViewModel;
import btuguides.repository.PartnerRepository;
import btuguides.service.PartnerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public Partners findById(String id) {
        return partnerRepository.findById(id).orElse(null);
    }

    @Override
    public void remove(String id) {
        partnerRepository.deleteById(id);
    }

    @Override
    public Object findAll() {
        List<TableViewModel> list=new ArrayList<>();
        partnerRepository.findAll().forEach(e -> {
            TableViewModel tb=new TableViewModel();
            tb.setName(e.getName());
            tb.setId(e.getId());
            list.add(tb);
        });
        return list;
    }

    @Override
    public Object find() {
        return partnerRepository.findAll();
    }
}
