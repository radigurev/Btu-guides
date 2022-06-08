package btuguides.service.implementation;

import btuguides.models.binding.TranslateBindingModel;
import btuguides.models.entity.translate;
import btuguides.models.view.TableViewModel;
import btuguides.repository.TranslateRepository;
import btuguides.service.TranslateService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TranslatesService implements TranslateService {

    private final TranslateRepository translateRepository;
    private final ModelMapper modelMapper;
    public TranslatesService(TranslateRepository translateRepository, ModelMapper modelMapper) {
        this.translateRepository = translateRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void save(TranslateBindingModel translateBindingModel) {
        translateRepository.save(modelMapper.map(translateBindingModel, translate.class));
    }

    @Override
    public translate findById(String id) {
        return translateRepository.findById(id).orElse(null);
    }

    @Override
    public void remove(String id) {
        translateRepository.deleteById(id);
    }

    @Override
    public Object findAll() {
            List<TableViewModel> list=new ArrayList<>();
            translateRepository.findAll().forEach(e -> {
                TableViewModel tb=new TableViewModel();
                tb.setName(e.getTitle());
                tb.setId(e.getId());
                list.add(tb);
            });
            return list;
        }

    @Override
    public Object find() {
        return translateRepository.findAll();
    }
}

