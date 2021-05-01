package me.kalpha.trapi.ehub.service;

import lombok.extern.slf4j.Slf4j;
import me.kalpha.trapi.ehub.entity.Eqp1Tr;
import me.kalpha.trapi.ehub.entity.Eqp1TrDet;
import me.kalpha.trapi.ehub.entity.Eqp1TrDto;
import me.kalpha.trapi.ehub.repository.Eqp1TrDetRepository;
import me.kalpha.trapi.ehub.repository.Eqp1TrRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class Eqp1TrService {
    @Autowired
    Eqp1TrRepository trRepository;
    @Autowired
    Eqp1TrDetRepository trDetRepository;
    @Autowired
    Eqp1TrProducerService trProducerService;
    @Autowired
    ModelMapper modelMapper;

    @Transactional
    public Eqp1Tr createTr(Eqp1TrDto eqp1TrDto) {
        Eqp1Tr eqp1Tr = modelMapper.map(eqp1TrDto, Eqp1Tr.class);
        List<Eqp1TrDet> eqp1TrDets = eqp1TrDto.getEqp1TrDetDtos().stream()
                .map(o -> o.toEntity(eqp1Tr))
                .collect(Collectors.toList());

        // Save at Eqp1Tr, Eqp1TrDet
        // CascadeType.ALL로 지정되어 있지 않기 때문에 따로 저장할 필요가 없다. 영속성 전파됨
        trRepository.save(eqp1Tr);
        log.info("eqq1Tr : {}({})", eqp1Tr, eqp1TrDets.size());

        // Publish to app.topic.name Topic
        trProducerService.sendMessage(eqp1Tr);

        return eqp1Tr;
    }
}
