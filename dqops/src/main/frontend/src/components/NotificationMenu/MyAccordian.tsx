import React, { useState } from 'react';
import SvgIcon from '../SvgIcon';

interface AccordionItem {
  title: string;
  content: string;
}

const MyAccordion: React.FC = () => {
  const [activeIndex, setActiveIndex] = useState<number | null>(null);

  const toggleAccordion = (index: number) => {
    setActiveIndex((prevIndex) => (prevIndex === index ? null : index));
  };

  const accordionItems: AccordionItem[] = [
    {
      title: 'Section 1',
      content:
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla ultrices aliquam mi, ac consectetur metus tincidunt in.'
    }
  ];

  return (
    <div className="accordion">
      {accordionItems.map((item, index) => (
        <div className="accordion-item" key={index}>
          <button
            className={`accordion-title font bold ${
              activeIndex === index ? 'active' : ''
            }`}
            onClick={() => toggleAccordion(index)}
          >
            {item.title}
            <SvgIcon name="chevron-down" />
          </button>
          {activeIndex === index && (
            <div className="accordion-content">{item.content}</div>
          )}
        </div>
      ))}
    </div>
  );
};

export default MyAccordion;
