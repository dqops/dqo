import clsx from 'clsx';
import React from 'react';

import { LabelModel } from '../../api';
import SectionWrapper from '../Dashboard/SectionWrapper';
import { limitTextLength } from '../../utils';
type TLabel = LabelModel & { clicked: boolean };
type TLabelSectionWrapper = {
  labels: TLabel[];
  onChangeLabels: (index: number) => void;
  className?: string;
};

export default function LabelsSectionWrapper({
  labels,
  onChangeLabels,
  className
}: TLabelSectionWrapper) {
  return (
    <SectionWrapper
      title="Filter by labels"
      className={className}
    >
      {labels.map((label, index) => (
        <div
          className={clsx(
            'flex gap-2 mb-2 cursor-pointer whitespace-normal break-all',
            {
              'font-bold text-gray-700': label.clicked,
              'text-gray-500': !label.clicked
            }
          )}
          key={index}
          onClick={() => onChangeLabels(index)}
        >
          <span>{limitTextLength(label.label, 17)}</span>({label.labels_count})
        </div>
      ))}
    </SectionWrapper>
  );
}
